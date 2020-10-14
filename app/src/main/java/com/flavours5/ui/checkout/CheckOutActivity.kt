package com.flavours5.ui.checkout

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.adapters.CartAddListAdatper
import com.flavours5.adapters.DateListAdatper
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.DateResponse
import com.flavours5.models.MyAddressResponse
import com.flavours5.models.OrderResponse
import com.flavours5.models.TimeResponse
import com.flavours5.ui.address.AddAddressActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckOutActivity : AppCompatActivity(), OnResponseInterface,
    CartAddListAdatper.AddInterfaceMethod,
    DateListAdatper.SelectDateInterface {
    private lateinit var localityResponse: MutableList<DateResponse.DataBean>
    private val process = Progress()
    private lateinit var dialog: Dialog
    private lateinit var options: ActivityOptions
    private var paymentMode: String = ""
    private var strDeliveryMode: String = ""
    private lateinit var txtAddAddress: TextView
    private var amount = 0.0
    private lateinit var username: String
    private var deliveryCharge = 0.0
    private lateinit var txtFinalAmount: TextView
    private lateinit var txtSelectDate: TextView
    private lateinit var txtSelectAddress: TextView
    private lateinit var txtSelectTime: TextView
    private var userAddressResponse: MutableList<MyAddressResponse.DataBean> = ArrayList()
    private var timeSlot = ArrayList<String>()
    private var dateSlot = ArrayList<String>()
    private var strDeliveryDate: String = ""
    private lateinit var btnPlaceOrder: TextView
    private var strDeliveryTime: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)
        (v.findViewById(R.id.title) as TextView).text = "Order Summary"
        supportActionBar?.customView = v

        options =
            ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        username = ClsGeneral.getPreferences(this, "username").toString()

        try {
            if (intent != null && intent.hasExtra("total")) {
                amount = intent.getDoubleExtra("total", 0.0)
                deliveryCharge = intent.getDoubleExtra("DeliveryCharge", 0.0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        initView()

    }

    private fun initView() {
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
        txtFinalAmount = findViewById(R.id.txtFinalAmount)
        txtSelectDate = findViewById(R.id.txtSelectDate)
        txtSelectTime = findViewById(R.id.txtselectTime)
        txtSelectAddress = findViewById(R.id.activity_cart_txtAddress)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        txtAddAddress = findViewById(R.id.addnewaddress)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.radioButtonDelivery === checkedId) {
                paymentMode = "Card on delivery"
            } else if (checkedId == R.id.radioButtonPick) {
                paymentMode = "COD"
            }
        }

        txtSelectDate.setOnClickListener {
            openDateDialog()
        }

        txtSelectTime.setOnClickListener {
            if (txtSelectDate.text.toString() != null && txtSelectDate.text.toString() == "") {
                Toast.makeText(
                    this@CheckOutActivity,
                    "First Select Date Slot",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                openTimeDialog()
            }
        }

        val radioGroup1: RadioGroup = findViewById(R.id.radioDeliveryMode)

        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            if (R.id.radioTruckDeliveryMode === checkedId) {
                strDeliveryMode = "Grocery Truck Mode"
            } else if (checkedId == R.id.radioButtonEcomm) {
                strDeliveryMode = "E-Commerce Mode"
            }
        }

        txtAddAddress.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@CheckOutActivity, AddAddressActivity::class.java)
            startActivity(intent)
        })

        val txtDelivery: TextView = findViewById(R.id.txtDeliveryCharge)
        txtDelivery.text = getString(R.string.rupees_symbol) + deliveryCharge
        txtFinalAmount.text = getString(R.string.rupees_symbol) +( amount - deliveryCharge)

        txtSelectAddress.setOnClickListener(View.OnClickListener {
            if (userAddressResponse!=null && userAddressResponse.size>0)
            openAddressDialog()
            else
                Toast.makeText(
                    this@CheckOutActivity,
                    "Address not found. Please add an address. ",
                    Toast.LENGTH_SHORT
                ).show()

        })


        btnPlaceOrder.setOnClickListener {
            if (validateInputData()) {
                if (!Utility.isConnectingToInternet(this)) {
                    dialog = Utility.createErrorDialog(
                        this,
                        getString(R.string.network_error)
                    )!!
                    dialog.show()
                } else {
                    makeOrder()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

        if (!Utility.isConnectingToInternet(this)) {
            dialog = Utility.createErrorDialog(
                this,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            getAddlist()
            getDatelist()
        }

    }

    private var dialogPop: BottomSheetDialog? = null

    private fun openDateDialog() {
        dialogPop = BottomSheetDialog(this@CheckOutActivity)
        dialogPop!!.setContentView(R.layout.dialog_date_list)
        val recyclerView: RecyclerView =
            dialogPop!!.findViewById(R.id.dialog_cart_address_recycler)!!
        val horizontalLayoutManagaer =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = horizontalLayoutManagaer
        dialogPop!!.setCancelable(true)
        dialogPop!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogPop!!.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialogPop!!.show()
        setDateList(recyclerView, dateSlot, "date")
    }

    private fun openTimeDialog() {
        dialogPop = BottomSheetDialog(this@CheckOutActivity)
        dialogPop!!.setContentView(R.layout.dialog_date_list)
        val recyclerView: RecyclerView? =
            dialogPop!!.findViewById(R.id.dialog_cart_address_recycler)
        val horizontalLayoutManagaer =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.layoutManager = horizontalLayoutManagaer
        dialogPop!!.setCancelable(true)
        dialogPop!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogPop!!.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialogPop!!.show()
        if (timeSlot != null && timeSlot.size > 0) {
            setDateList(recyclerView, timeSlot, "time")
        } else {
            Toast.makeText(
                this@CheckOutActivity,
                "Not time slots available. Please select another date.",
                Toast.LENGTH_SHORT
            ).show()
            strDeliveryTime = ""
            txtSelectTime.hint = "Select Time slot"
        }
    }

    private fun setDateList(recyclerView: RecyclerView, dateSlot: ArrayList<String>, type: String) {
        val dateListAdatper = DateListAdatper(this, applicationContext, dateSlot, type)
        recyclerView.adapter = dateListAdatper
    }

    private fun getAddlist() {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callGetAddressList(username)
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))
        ResponseListner(this).getResponse(call,this)
    }

    private fun getDatelist() {
        val call = APIClient().apiInterface.callEcomDateList()
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))
        ResponseListner(this).getResponse(call,this)
    }

    private fun getTimelist(date: String) {
        process.dialog.show()
        val call = APIClient().apiInterface.callEomTimeList(date)
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))
        ResponseListner(this).getResponse(call,this)
    }


    private fun makeOrder() {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.makeOrder(
            username,
            selecetdAdderss.deliveryPersonName,
            selecetdAdderss.deliveryPersonMOB,
            paymentMode,
            amount.toString(),
            strDeliveryDate,
            strDeliveryTime,
            strDeliveryMode,
            "",
            selecetdAdderss.id.toString(),
            deliveryCharge
        )
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))
        ResponseListner(this).getResponse(call,this)
    }


    private lateinit var addressDialog: BottomSheetDialog

    private fun openAddressDialog() {
        addressDialog = BottomSheetDialog(this@CheckOutActivity)
        addressDialog.setContentView(R.layout.dialog_date_list)
        val recyclerView: RecyclerView =
            addressDialog.findViewById(R.id.dialog_cart_address_recycler)!!
        val horizontalLayoutManagaer =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = horizontalLayoutManagaer
        addressDialog.setCancelable(true)
        addressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addressDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        addressDialog.show()
        setAddress(recyclerView)
    }

    private fun setAddress(recyclerView: RecyclerView) {
        val cartAddListAdatper = CartAddListAdatper(this, applicationContext, userAddressResponse)
        recyclerView.adapter = cartAddListAdatper
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onApiResponse(response: Any?) {
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        process.dialog.dismiss()
        if (response != null) {
            if (response is DateResponse) {
                localityResponse = ArrayList()
                dateSlot = ArrayList()
                if (response.status != null && response.status == "Success") {
                    if (response.data != null && response.data.size > 0) {
                        localityResponse = response.data
                        for (x in localityResponse) {
                            dateSlot.add(x.date)
                        }
                    }
                }
            } else if (response is MyAddressResponse) {
                if (response.status != null && response.status == "Success") {
                    if (response.data != null && response.data.size > 0) {
                        if (userAddressResponse!=null)
                        userAddressResponse.clear()
                        userAddressResponse = response.data
                    }
                }
            } else if (response is TimeResponse) {
                var timeResponse: List<TimeResponse.DataBean> = ArrayList()
                timeSlot = ArrayList()
                if (response.status != null && response.status == "Success") {
                    if (response.data != null && response.data.size > 0) {
                        timeResponse = response.data
                        for (x in timeResponse) {
                            timeSlot.add(x.starttime + " To " + x.endtime)
                        }
                    }
                }
            } else if (response is OrderResponse) {

                if (response.status != null && response.status == "Success") {
                    if (response.data!=null && response.data.orderId != null) {

                        val orderId: String = response.data.orderId
                        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                        val currentDate = sdf.format(Date())
                        println(" C DATE is  $currentDate")
                        ClsGeneral.setPreferences(this, "cartsize","0")
                        val intent = Intent(this, OrderConfirmationActivity::class.java)
                        intent.putExtra("ProductId", orderId)
                        intent.putExtra("Date", currentDate)
                        intent.putExtra("address", response.data.address)
                        intent.putExtra("number", response.data.mobilenumber)


                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun validateInputData(): Boolean {

        var s: Boolean = false
        if (strDeliveryMode == null || strDeliveryMode.length == 0) {
            Toast.makeText(this, "Choose Delivery Mode!", Toast.LENGTH_SHORT).show()
        } else if (paymentMode == null || paymentMode.length == 0) {
            Toast.makeText(this, "Choose Delivery type!", Toast.LENGTH_SHORT).show()
        } else if (strDeliveryTime == null || strDeliveryTime == "") {
            Toast.makeText(this, "Select Time!", Toast.LENGTH_SHORT).show()
        } else if (strDeliveryDate == null || strDeliveryTime == "") {
            Toast.makeText(this, "Select Date!", Toast.LENGTH_SHORT).show()
        } else if (txtSelectAddress.text.toString() == "Select an Address") {
            Toast.makeText(this, "Please select address!", Toast.LENGTH_SHORT).show()
        } else {
            s = true
        }
        return s
    }

    override fun onApiFailure(message: String?) {
        Log.d("TAG", "onApiFailure: " + Gson().toJson(message))
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(this, getString(R.string.error))!!
        dialog.show()
    }

    private var selecetdAdderss: MyAddressResponse.DataBean = MyAddressResponse.DataBean()

    override fun AddressInterfaceMethod(position: Int) {

        try {
            addressDialog.dismiss()
        }
        catch (e: Exception){

        }
      try{
          selecetdAdderss = userAddressResponse[position]
          txtSelectAddress.text = (userAddressResponse[position].deliveryPersonName.toString() + "("
                  + userAddressResponse[position].deliveryPersonMOB + ")" + "\n" + userAddressResponse[position].landmark + ", " + userAddressResponse[position].locality
                  + " ," + userAddressResponse[position].city
                  + ", " + userAddressResponse[position].statename + ", " + userAddressResponse[position].pincode)
      } catch (e: Exception){

      }
    }

    override fun selectDate(position: Int, type: String?) {
        dialogPop!!.dismiss()
        if (type == "date") {
            val date = dateSlot[position]
            txtSelectDate.text = date
            strDeliveryDate = date
            getTimelist(date)
        } else {
            val time = timeSlot[position]
            strDeliveryTime = time
            txtSelectTime.text = time
        }
    }
}
