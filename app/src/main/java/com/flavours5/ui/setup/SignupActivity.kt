package com.flavours5.ui.setup

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.LoginResponse
import com.flavours5.models.OtpVerifyResponse
import com.flavours5.models.SignupResponse
import com.flavours5.ui.main.HomeActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_home.*

class SignupActivity : AppCompatActivity(), OnResponseInterface {
    private lateinit var options: ActivityOptions
    private lateinit var edtPass: EditText
    private lateinit var edtUsername: EditText
    private lateinit var pass: String
    private lateinit var username: String
    private val process = Progress()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)

        initView()

    }


    private fun initView() {
        edtPass = findViewById(R.id.edtPass)
        edtUsername = findViewById(R.id.edtEmail)
    }

    private lateinit var dialog: Dialog

    fun signup(view: View) {
        username = edtUsername.text.toString()
        pass = edtPass.text.toString()

        if (username.isEmpty() && !Utility.isValidMobile(username)) {
            dialog = Utility.createErrorDialog(
                this@SignupActivity,
                "Please enter valid mobile number. "
            )!!
            dialog.show()
        } else if (pass.isEmpty()) {
            dialog = Utility.createErrorDialog(
                this@SignupActivity,
                "Password can not be left blank."
            )!!
            dialog.show()
        } else if (!Utility.isConnectingToInternet(this@SignupActivity)) {
            dialog = Utility.createErrorDialog(
                this@SignupActivity,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            callSignup(username, pass)
        }


//        val i = Intent(applicationContext, LoginActivity::class.java)
//        startActivity(i, options.toBundle())
//        finish()
    }

    private lateinit var dialogOtp: Dialog

    private fun createDialog() {
        dialogOtp = Dialog(this)
        dialogOtp.setContentView(R.layout.verify_otp_dialog)
        dialogOtp.setCancelable(true)
//        dialogOtp.setCanceledOnTouchOutside(false)
        dialogOtp.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogOtp.window!!.attributes.windowAnimations = R.style.DialogAnimation
        val txtSubmit: TextView = dialogOtp.findViewById(R.id.dialog_submit_otp)
        val txtResend: TextView = dialogOtp.findViewById(R.id.dialog_resend_btn)
        val edtOtp: EditText = dialogOtp.findViewById(R.id.edtOtp)

        txtSubmit.setOnClickListener { view: View? ->
            verifyOTP(edtOtp.text.toString(),username)
        }
        dialogOtp.show()
        txtResend.setOnClickListener { view: View? ->
            resendOtp(username)
        }

    }

    private fun callSignup(username: String, pass: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callSignup(username, pass)
        ResponseListner(this).getResponse(call,this)
    }


    private fun verifyOTP(otp: String, username: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callVerifySignupOtp(username, otp)
        ResponseListner(this).getResponse(call,this)
    }

    fun login(view: View) {
        val i = Intent(applicationContext, LoginActivity::class.java)
        startActivity(i, options.toBundle())
        finish()
    }

    private fun resendOtp(username: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callResendForSignupOtp(username)
        ResponseListner(this).getResponse(call,this)
    }

    override fun onApiResponse(response: Any?) {
        try {
            process.dialog.dismiss()
        } catch (e: Exception){

        }

        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))

        if (response != null) {
            if (response is SignupResponse){
                val signupResponse: SignupResponse = response as SignupResponse

                if (signupResponse.Status != null && signupResponse.Status == "Success") {
                    if (signupResponse.Messege!=null && signupResponse.Messege == "OTP Send On Your Mobile Number !!!"){
                        createDialog()
                    } else{
                        dialog = Utility.createErrorDialog(
                            this@SignupActivity, signupResponse.Messege)!!
                        dialog.show()
                    }
                } else {
                    dialog = Utility.createErrorDialog(
                        this@SignupActivity, signupResponse.Messege)!!
                    dialog.show()
                }
            } else    if (response is LoginResponse) {
                val loginReponse: LoginResponse = response

                if (loginReponse.Status != null && loginReponse.Status == "Success") {
                    dialogOtp!!.dismiss()
                    if (loginReponse.Messege != null && loginReponse.Messege == "Registration Successfully !!!") {
                        Toast.makeText(
                            this@SignupActivity,
                            "Registration Successfull.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val i = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(i, options.toBundle())
                        finish()
                    } else {
                        dialog = Utility.createErrorDialog(
                            this@SignupActivity, loginReponse.Messege
                        )!!
                        dialog.show()
                    }

                } else {
                    dialog = Utility.createErrorDialog(
                        this@SignupActivity, loginReponse.Messege
                    )!!
                    dialog.show()
                }

            } else if (response is OtpVerifyResponse) {
                val otpVerifyResponse: OtpVerifyResponse = response

                if (otpVerifyResponse.Status != null && otpVerifyResponse.Status == "Success") {
                    if (otpVerifyResponse.Messege != null &&
                        ( otpVerifyResponse.Messege==("OTP Re-Send On Mobile Number !!!"))) {

                    } else {
                        dialog = Utility.createErrorDialog(
                            this@SignupActivity, otpVerifyResponse.Messege
                        )!!
                        dialog.show()
                    }

                } else {
                    dialog = Utility.createErrorDialog(
                        this@SignupActivity, otpVerifyResponse.Messege
                    )!!
                    dialog.show()
                }
            }
        }
    }

    override fun onApiFailure(message: String?) {
        try {
            dialog = Utility.createErrorDialog(
                this@SignupActivity, getString(R.string.error)
            )!!
            dialog.show()
        } catch (e: Exception){

        }
    }
}
