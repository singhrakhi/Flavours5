package com.flavours5.ui.address

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.adapters.AddressAdapter
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.AddressResponse
import com.flavours5.models.MyAddressResponse
import com.flavours5.ui.main.HomeActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson

class MyAddressActivity : AppCompatActivity(), OnResponseInterface, AddressAdapter.AddressActionInterface {
    private lateinit var options: ActivityOptions
    private lateinit var username: String
    private val process = Progress()
    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_address)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)
        (v.findViewById(R.id.title) as TextView).text = "My Address"
        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        supportActionBar?.customView = v
        username = ClsGeneral.getPreferences(this, "username").toString()

        recyclerView = findViewById(R.id.allAddresslist)

        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

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
        }
    }



    private fun getAddlist() {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callGetAddressList(username)
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))
        ResponseListner(this).getResponse(call,this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun addAddress(view: View) {
        val i = Intent(this, AddAddressActivity::class.java)
        startActivity(i, options.toBundle())
    }

    private var userAddressResponse: MutableList<MyAddressResponse.DataBean> = ArrayList()

    override fun onApiResponse(response: Any?) {
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        process.dialog.dismiss()
        if (response!=null){
            if (response is MyAddressResponse){
                if (response.status!=null && response.status=="Success"){
                    if (response.data!=null && response.data.size>0){
                        if (userAddressResponse!=null)
                            userAddressResponse.clear()
                        userAddressResponse = response.data
                    } else{
                        if (userAddressResponse!=null)
                            userAddressResponse.clear()
                    }
                }
                setAdapter(userAddressResponse)

            } else if (response is AddressResponse){
                if (response.status!=null && response.status == "Success"){
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    getAddlist()
                } else{
                    dialog = Utility.createErrorDialog(this, response.message)!!
                    dialog.show()
                }
            }
        }
    }

    private fun setAdapter(userAddressResponse: MutableList<MyAddressResponse.DataBean>) {
        userAddressResponse.reverse()
        recyclerView.adapter = AddressAdapter(this,userAddressResponse,this)

    }

    override fun onApiFailure(message: String?) {
        Log.d("TAG", "onApiFailure: " + Gson().toJson(message))

        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(this, getString(R.string.error))!!
        dialog.show()
    }

    override fun clickAction(tag: String?, i: Int) {
        if (tag == "edit") {
            val intent = Intent(this, AddAddressActivity::class.java)
            intent.putExtra("addId", userAddressResponse[i].id)
            intent.putExtra("data", Gson().toJson(userAddressResponse[i]))
            startActivity(intent, options.toBundle())
        } else if (tag == "delete") {
            deleteAddress(userAddressResponse[i].id.toString() + "")
        }
    }

    private fun deleteAddress(s: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.deleteAddress(s)
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))
        ResponseListner(this).getResponse(call,this)
    }


}
