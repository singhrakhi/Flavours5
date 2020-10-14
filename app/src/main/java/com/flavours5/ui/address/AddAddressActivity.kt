package com.flavours5.ui.address

import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.AddressResponse
import com.flavours5.models.LocalityResponse
import com.flavours5.models.MyAddressResponse
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson

class AddAddressActivity : AppCompatActivity(), OnResponseInterface {
    private val process = Progress()
    private lateinit var dialog: Dialog
    private lateinit var spinner: Spinner
    private lateinit var options: ActivityOptions
    private lateinit var selectedState: String
    private lateinit var txtLocality: TextView
    private lateinit var edtLandMark: EditText
    private lateinit var edtCity: EditText
    private lateinit var edtPin: EditText
    private lateinit var edtAdd: EditText
    private lateinit var edtMobile: EditText
    private lateinit var edtName: EditText
    private lateinit var userid: String
    private lateinit var deliveryPersonName: String
    private var deliveryPersonMOB: String=""
    private var mainAddress: String=""
    private var pincode: String=""
    private var city: String=""
    private var landmark: String=""
    private var locality: String=""
    private var id: String=""
    private lateinit var btnAdd: TextView
    private var addressBean: MyAddressResponse.DataBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)

        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)

        if (intent.hasExtra("addId")){
            id = intent.getIntExtra("addId",0).toString()
            (v.findViewById(R.id.title) as TextView).text = "Edit Address"
            addressBean = Gson().fromJson(intent.getStringExtra("data"), MyAddressResponse.DataBean::class.java)
        } else{
            (v.findViewById(R.id.title) as TextView).text = "Add New Address"
        }

        supportActionBar?.customView = v
        initView()
    }

    private fun initView() {
        userid = ClsGeneral.getPreferences(this, "userid").toString()
        btnAdd = findViewById(R.id.btnAdd)
        spinner = findViewById(R.id.spineer_state)
        localityArrayList  = ArrayList()
        edtName = findViewById(R.id.edt_user_name)
        edtMobile = findViewById(R.id.edt_mobile_number)
        edtAdd = findViewById(R.id.edt_address_line_1)
        edtPin = findViewById(R.id.edt_pin_code)
        edtCity = findViewById(R.id.edt_city)
        edtLandMark = findViewById(R.id.edLandMark)
        txtLocality = findViewById(R.id.txtLocality)
        localityArrayList = ArrayList()
        setSpinner()
        getLocalityData()

        if (addressBean!=null){
            edtName.setText(addressBean!!.deliveryPersonName)
            edtName.setText(addressBean!!.deliveryPersonName)
            edtLandMark.setText(addressBean!!.landmark)
            edtMobile.setText(addressBean!!.deliveryPersonMOB)
            edtCity.setText(addressBean!!.city)
            edtPin.setText(addressBean!!.pincode.toString())
            edtAdd.setText(addressBean!!.mainAddress)
            txtLocality.text = addressBean!!.locality
        }

        txtLocality.setOnClickListener {
            localityDataDialog()
        }

        btnAdd.setOnClickListener {

            if (validateInputData()){
                addAddressData()
            }
        }
    }

    private fun setSpinner() {
        val cityArray = arrayOf("Select State", "Haryana")
//        spinner.isEnabled = false
        val cityList = listOf(*cityArray)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(1)

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    selectedState = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun validateInputData(): Boolean {
        deliveryPersonName = edtName.text.toString().trim { it <= ' ' }
        deliveryPersonMOB = this.edtMobile.text.toString().trim { it <= ' ' }
        mainAddress = edtAdd.text.toString().trim { it <= ' ' }
        pincode = edtPin.text.toString().trim { it <= ' ' }
        city = edtCity.text.toString().trim { it <= ' ' }
        locality = txtLocality.text.toString().trim { it <= ' ' }
        landmark = edtLandMark.text.toString().trim { it <= ' ' }
        var s = false
        if (deliveryPersonName == "") {
            s = false
            Toast.makeText(this, "Enter Name!", Toast.LENGTH_SHORT).show()
        } else if (deliveryPersonMOB == "") {
            s = false
            Toast.makeText(this, "Enter mobile number!", Toast.LENGTH_SHORT).show()
        } else if (deliveryPersonMOB.length != 10) {
            s = false
            Toast.makeText(this, "Enter valid mobile number!!", Toast.LENGTH_SHORT).show()
        } else if (mainAddress == "") {
            s = false
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show()
        } else if (pincode == "") {
            s = false
            Toast.makeText(this, "Enter PIN!", Toast.LENGTH_SHORT).show()
        } else if (city == "") {
            s = false
            Toast.makeText(this, "Select City!", Toast.LENGTH_SHORT).show()
        } else if (landmark == "") {
            s = false
            Toast.makeText(this, "Enter Landmark!", Toast.LENGTH_SHORT).show()
        } else if (selectedState == "") {
            s = false
            Toast.makeText(this, "Select State!", Toast.LENGTH_SHORT).show()
        } else if (locality == "") {
            s = false
            Toast.makeText(this, "Select Locality!", Toast.LENGTH_SHORT).show()
        } else {
            s = true
        }
        return s
    }


    private fun addAddressData() {
        if (id!=null && id.length>0){

        } else{
            id = ""
        }
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.addAddress(userid,deliveryPersonName,
            deliveryPersonMOB,mainAddress,pincode,selectedState,city,landmark,locality,id)

        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))

        ResponseListner(this).getResponse(call,this)
    }

    private fun getLocalityData() {
        if (Utility.isConnectingToInternet(this)) {
            process.show(this, "Please Wait...")
            val call = APIClient().apiInterface.callLocalityData()
            ResponseListner(this).getResponse(call,this)
        } else {
            Toast.makeText(this, resources.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
        }
    }

   private var localityArrayList: ArrayList<String>? = null

    private fun localityDataDialog() {
        try {
            if (localityArrayList!=null && localityArrayList!!.size>0){
                val dateArray: Array<String> = localityArrayList!!.toTypedArray()
                val mBuilder: AlertDialog.Builder = AlertDialog.Builder(this@AddAddressActivity)
                mBuilder.setTitle("Choose Locality")
                mBuilder.setSingleChoiceItems(dateArray, -1,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        txtLocality.text = dateArray[i]
                        dialogInterface.dismiss()
                    })
                val mDialog: AlertDialog = mBuilder.create()
                mDialog.show()
            }

        } catch (e: Exception) {
            e.printStackTrace()
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

    private lateinit var localityResponse: MutableList<LocalityResponse.DataBean>

    override fun onApiResponse(response: Any?) {
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))

        process.dialog.dismiss()
        if (response!=null){
            if (response is LocalityResponse){
                localityResponse = ArrayList()
                if (response.status!=null && response.status=="Success"){
                    if (response.data!=null && response.data.size>0){
                        localityResponse = response.data
                        for (x in localityResponse){
                            localityArrayList?.add(x.localityname)
                        }
                    }
                }
            } else if (response is AddressResponse){

                if (response.status!=null && response.status == "Success"){
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    finish()
                } else{
                    dialog = Utility.createErrorDialog(this, response.message)!!
                    dialog.show()
                }

            }
        }
    }

    override fun onApiFailure(message: String?) {
        Log.d("TAG", "onApiFailure: " + Gson().toJson(message))

        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(this, getString(R.string.error))!!
        dialog.show()
    }
}
