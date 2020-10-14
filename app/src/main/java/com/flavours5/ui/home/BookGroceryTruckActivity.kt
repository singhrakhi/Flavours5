package com.flavours5.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.GroceryDateResponse
import com.flavours5.models.GroceryTimeResponse
import com.flavours5.models.OtpVerifyResponse
import com.flavours5.models.ResendOtpResponse
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson

class BookGroceryTruckActivity : AppCompatActivity(), OnResponseInterface {
    private var edTxtDeliveryTime: TextView? = null
    private var edTxtDeliveryDate:TextView? = null
    private var txtBookNow:TextView? = null
   private var edTxtUserName: EditText? = null
   private var edTxtContactNo:EditText? = null
   private var edTxtAddress:EditText? = null
    private var edTxtRequirment:EditText? = null
    private var userid: String=""
    var layoutForm: ScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_grocery_truck)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)
        userid = ClsGeneral.getPreferences(this@BookGroceryTruckActivity, "userid").toString()

        (v.findViewById(R.id.title) as TextView).text = "Book Grocery Truck"

        supportActionBar?.customView = v

        initView()

    }

    private fun initView() {
        layoutForm = findViewById(R.id.layoutForm)

        edTxtUserName = findViewById(R.id.edTxtUserName)
        edTxtContactNo = findViewById(R.id.edTxtContactNo)
        edTxtAddress = findViewById(R.id.edTxtAddress)
        edTxtRequirment = findViewById(R.id.edTxtRequirment)

        edTxtDeliveryDate = findViewById(R.id.edTxtDeliveryDate)
        edTxtDeliveryTime = findViewById(R.id.edTxtDeliveryTime)

        txtBookNow = findViewById(R.id.txtBookNow)
        txtBookNow!!.setOnClickListener(View.OnClickListener {
            if (!Utility.isConnectingToInternet(this)) {
                dialog = Utility.createErrorDialog(
                    this,
                    getString(R.string.network_error)
                )!!
                dialog.show()
            } else {
                bookNow()
            }

        })

        if (!Utility.isConnectingToInternet(this)) {
            dialog = Utility.createErrorDialog(
                this,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            getDateSlot()
        }

        edTxtDeliveryTime!!.setOnClickListener {

            if (timeSlot!=null && timeSlot.size>0){
                deliveryTime()
            } else{
                ToastMethod("No time slot available. Please choose another date.")
                edTxtDeliveryTime!!.text = ""
            }
        }

        edTxtDeliveryDate!!.setOnClickListener {

            if (!Utility.isConnectingToInternet(this)) {
                dialog = Utility.createErrorDialog(
                    this,
                    getString(R.string.network_error)
                )!!
                dialog.show()
            } else {
               deliveryDate()
            }

        }

    }

    private fun getTimelist(id: String) {
        process.dialog.show()
        val call = APIClient().apiInterface.callGroceryTimeList(id)
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))
        ResponseListner(this).getResponse(call, this@BookGroceryTruckActivity)
    }

    private fun getDateSlot() {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callGroceryDateList()
        ResponseListner(this).getResponse(call,this@BookGroceryTruckActivity)
    }

    private fun deliveryDate() {
        try {
            val dateArray = dateSlot.toTypedArray()
            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@BookGroceryTruckActivity)
            mBuilder.setTitle("Choose Delivery Date")
            mBuilder.setSingleChoiceItems(dateArray, -1,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    edTxtDeliveryDate!!.text = dateArray[i]
                    selectedId = dateSlotHap[dateArray[i]].toString()
                    getTimelist(selectedId)
                    dialogInterface.dismiss()
                })
            val mDialog: AlertDialog = mBuilder.create()
            mDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deliveryTime() {
        try {
            val time = timeSlot.toTypedArray()
            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@BookGroceryTruckActivity)
            mBuilder.setTitle("Choose Delivery Time")
            mBuilder.setSingleChoiceItems(
                time, -1
            ) { dialogInterface, i ->
                edTxtDeliveryTime!!.text = time[i]
                dialogInterface.dismiss()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    private lateinit var dialog: Dialog
    private val process = Progress()
    private lateinit var localityResponse: MutableList<GroceryDateResponse.DataBean>
    private var timeSlot = ArrayList<String>()
    private var dateSlot = ArrayList<String>()
    private var dateSlotHap= HashMap<String,String>()
    private var timeSlotHap= HashMap<String,String>()

    private var selectedId = ""

    private fun bookGroceryTruck(
        strName: String, strMobile: String, strAddress: String,
        strDeliveryDate: String, strDeliveryTime: String, strDetails: String
    ) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.bookTruck("",strName,strAddress,
            strMobile,"",strDetails,userid,strDeliveryDate,strDeliveryTime,"")
        ResponseListner(this).getResponse(call,this@BookGroceryTruckActivity)
    }


    private fun ToastMethod(s: String) {
        val toast = Toast.makeText(
            applicationContext,
            s, Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

    private var otpVeryfyDialog: Dialog? = null
    private var isOtp = false
    private var edTxtOTP: EditText? = null
    private var btnVerifyOTP: TextView? = null
    private var txt_Resend_otp:TextView? = null
    private var isResend: Boolean = false
    private var strName: String? = null
    private var strMobile:String? = null
    private var strAddress:String? = null
    private var strDetails:String? = null
    private var strDeliveryDate:String? = null
    private var strDeliveryTime:String? = null

    private fun createOtpDialog() {
        otpVeryfyDialog = Dialog(this)
        otpVeryfyDialog!!.setContentView(R.layout.dialog_otp_verify)
        otpVeryfyDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        otpVeryfyDialog!!.show()
        edTxtOTP = otpVeryfyDialog!!.findViewById(R.id.edTxtOTP)
        txt_Resend_otp = otpVeryfyDialog!!.findViewById(R.id.txt_Resend_otp)
        btnVerifyOTP = otpVeryfyDialog!!.findViewById(R.id.btnVerifyOTP)
        btnVerifyOTP!!.setOnClickListener(View.OnClickListener {
            val strOTP: String = edTxtOTP!!.text.toString().trim { it <= ' ' }
            if (strOTP != "") {
                isOtp = true
                verifyOTPWebService(strOTP)
            } else ToastMethod("Enter OTP !")
        })
        txt_Resend_otp!!.setOnClickListener(View.OnClickListener {
            isResend = true
            strMobile = edTxtContactNo!!.text.toString().trim { it <= ' ' }
            if (strMobile != "") resendOTPWebService(strMobile!!) else ToastMethod("Enter mobile number")
        })
    }

    private fun resendOTPWebService(strMobile: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callResendOtpForGroceryTruck(strMobile)
        ResponseListner(this).getResponse(call,this@BookGroceryTruckActivity)
    }

    private fun verifyOTPWebService(strOTP: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.verifyOtp("",strName,strAddress,
            strMobile,"",strDetails,userid,strDeliveryDate,strDeliveryTime,"",strOTP)
        ResponseListner(this).getResponse(call,this@BookGroceryTruckActivity)
    }

    private fun bookNow() {
        strName = edTxtUserName!!.text.toString().trim { it <= ' ' }
        strMobile = edTxtContactNo!!.text.toString().trim { it <= ' ' }
        strAddress = edTxtAddress!!.text.toString().trim { it <= ' ' }
        strDetails = edTxtRequirment!!.text.toString().trim { it <= ' ' }
        strDeliveryDate = edTxtDeliveryDate!!.text.toString().trim { it <= ' ' }
        strDeliveryTime = edTxtDeliveryTime!!.text.toString().trim { it <= ' ' }
        if (strName == "") {
            ToastMethod("Enter Name")
        } else if (strMobile == "") {
            ToastMethod("Enter mobile number")
        } else if (strAddress == "") {
            ToastMethod("Enter address")
        } else if (strDeliveryDate == "") {
            ToastMethod("Select Delivery Date")
        } else if (strDeliveryTime == "") {
            ToastMethod("Select Delivery Time Slot")
        } else if (strDetails == "") {
            ToastMethod("Enter Details")
        } else {
            if (Utility.isValidMobile(strMobile!!)) {
                bookTruck(strName!!, strMobile!!, strAddress!!, strDeliveryDate!!, strDeliveryTime!!,
                    strDetails!!)
            }
        }
    }

    private fun bookTruck(strName: String, strMobile: String, strAddress: String, strDeliveryDate: String, strDeliveryTime: String, strDetails: String) {

        if (Utility.isConnectingToInternet(this)) {
            bookGroceryTruck(strName, strMobile, strAddress, strDeliveryDate, strDeliveryTime, strDetails)
        } else {
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show()
        }
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
        process.dialog.dismiss()
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        if (response != null) {
            if (response is OtpVerifyResponse){
                if (response.Status!=null && response.Status=="Success"){
                    Toast.makeText(this, "OTP Send on your mobile number !!!.", Toast.LENGTH_SHORT).show()

                    if (otpVeryfyDialog == null || !otpVeryfyDialog!!.isShowing) createOtpDialog() else {
                            otpVeryfyDialog!!.dismiss()
                            if (isOtp) {
                                Toast.makeText(this, "Booking success.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                } else{
                    if (response.Messege!=null)
                    Toast.makeText(this, response.Messege, Toast.LENGTH_SHORT).show()

                }

            } else  if (response is GroceryDateResponse){
                localityResponse = ArrayList()
                dateSlot = ArrayList()
                if (response.status!=null && response.status=="Success"){
                    if (response.data!=null && response.data.size>0){
                        localityResponse = response.data
                        for (x in localityResponse){
                            dateSlot.add(x.slotDate)
                            dateSlotHap.put(x.slotDate,x.id.toString())
                        }
                    }
                }
            }else if (response is GroceryTimeResponse) {
                var timeResponse: List<GroceryTimeResponse.DataBean> = ArrayList()
                timeSlot = ArrayList()
                if (response.status != null && response.status == "Success") {
                    if (response.data != null && response.data.size > 0) {
                        timeResponse = response.data
                        for (x in timeResponse) {
                            timeSlot.add(x.slotStartTime + " To " + x.slotEndTime)
                            timeSlotHap.put(x.slotStartTime + " To " + x.slotEndTime,x.id.toString())
                        }
                    }
                }
            } else if (response is ResendOtpResponse){
                if (response.status!=null && response.status == "Success"){
                    Toast.makeText(this,response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onApiFailure(message: String?) {
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(this, getString(R.string.error))!!
        dialog.show()
    }


}
