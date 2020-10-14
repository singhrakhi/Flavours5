package com.flavours5.ui.account

import android.app.ActivityOptions
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.ResendOtpResponse
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity(), OnResponseInterface {
    private val process = Progress()
    private lateinit var dialog: Dialog
    private lateinit var options: ActivityOptions
    private var username: String = ""
    private lateinit var edtNew: EditText
    private lateinit var edtNewConfim:EditText
    private  lateinit var edtOld:EditText
    private lateinit var txtSubmit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)
        (v.findViewById(R.id.title) as TextView).text = "Change Password"
        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        supportActionBar?.customView = v
        username = ClsGeneral.getPreferences(this, "username").toString()

        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)


        initView()

    }

    private fun initView() {
        edtNew = findViewById(R.id.edNewPass)
        edtNewConfim = findViewById(R.id.edTxtConPass)
        edtOld = findViewById(R.id.edTxtPass)

        txtSubmit = findViewById(R.id.btnSubmit)
        txtSubmit.setOnClickListener(View.OnClickListener {
            if (edtOld.getText().toString().isEmpty()) {
                edtOld.setError("Please enter old password.")
                edtOld.requestFocus()
            } else if (edtNew.getText().toString().isEmpty()) {
                edtNew.setError("Please enter new password.")
                edtNew.requestFocus()
            } else if (edtNewConfim.getText().toString().isEmpty()) {
                edtNewConfim.setError("Please confirm new password.")
                edtNewConfim.requestFocus()
            } else if (edtNew.getText().toString() != edtNewConfim.getText().toString()) {
                edtNewConfim.setError("Confirm password and new password should be same.")
                edtNewConfim.requestFocus()
            } else {
                changePassword()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changePassword() {
        if (Utility.isConnectingToInternet(this)) {
            process.show(this, "Please Wait...")
            val call = APIClient().apiInterface.callChangepass(username,edtOld.text.toString(),
                edtNew.text.toString(),edTxtConPass.text.toString())
            ResponseListner(this).getResponse(call,this)
        } else {
            Toast.makeText(this, resources.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onApiResponse(response: Any?) {
        process.dialog.dismiss()
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        if (response != null) {
            if (response is ResendOtpResponse){
                if (response.status!=null && response.status == "Success"){
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    finish()
                } else{
                   try {
                       dialog = Utility.createErrorDialog(this@ChangePasswordActivity,response.message)!!
                       dialog.show()
                   } catch (e: Exception){

                   }
                }
            }
        }

    }

    override fun onApiFailure(message: String?) {
        Log.d("TAG", "onApiFailure: $message")
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(this@ChangePasswordActivity, getString(R.string.error))!!
        dialog.show()
    }


}
