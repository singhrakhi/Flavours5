package com.flavours5.ui.setup

import android.Manifest
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.common.GPSTracker
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.LoginResponse
import com.flavours5.models.OtpVerifyResponse
import com.flavours5.ui.main.HomeActivity
import com.flavours5.ui.prodcuts.ProductsActivity
import com.flavours5.ui.search.SearchActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), OnResponseInterface {
    private lateinit var options: ActivityOptions
    private lateinit var edtPass: EditText
    private lateinit var edtUsername: EditText
    private lateinit var edtMobile: EditText
    private lateinit var pass: String
    private lateinit var username: String
    private val process = Progress()
    private lateinit var layoutVerify: LinearLayout
    private lateinit var layoutLogin: LinearLayout
    private lateinit var btnLogin: Button
    private lateinit var txtloginWithOtp: TextView
    private lateinit var txtSkip: TextView
    private lateinit var editOtp: EditText
    private var activtyName: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) { // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

            } else { // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2
                )
            }
        }

        if (intent.hasExtra("activity"))
            activtyName = intent.getStringExtra("activity")

        initView()
    }

    private fun initView() {
        edtPass = findViewById(R.id.edtPass)
        edtUsername = findViewById(R.id.edtPhone)
        edtMobile = findViewById(R.id.edtPhone1)
        layoutLogin = findViewById(R.id.layoutLogin)
        layoutVerify = findViewById(R.id.layoutVerify)
        btnLogin = findViewById(R.id.login);
        txtSkip = findViewById(R.id.skip)
        editOtp = findViewById(R.id.edtOTP)
        txtloginWithOtp = findViewById(R.id.loginWithOtp)
        txtloginWithOtp.text = "Login with Password"
        btnLogin.text = "Send OTP"
        txtloginWithOtp.text = "Resend OTP"
        layoutVerify.visibility = View.VISIBLE
        layoutLogin.visibility = View.GONE
        editOtp.visibility = View.GONE
//        txtSkip.text = "Login With Password"

        txtloginWithOtp.setOnClickListener {
            if (txtloginWithOtp.text.toString() == resources.getString(R.string.login_with_otp)) {
                btnLogin.text = "Send OTP"
                txtloginWithOtp.text = "Resend OTP"
                layoutVerify.visibility = View.VISIBLE
                layoutLogin.visibility = View.GONE
                editOtp.visibility = View.GONE
                txtSkip.text = "Login With Password"

            } else if (txtloginWithOtp.text=="Resend OTP"){
                val mobile = edtMobile.text.toString()

                if (mobile.isEmpty() && !Utility.isValidMobile(mobile)){
                    dialog = Utility.createErrorDialog(
                        this@LoginActivity,
                        "Please enter valid mobile number. "
                    )!!
                    dialog.show()
                } else{
                    resendOtp(mobile)
                }
            }
            else if (txtloginWithOtp.text == "Login With Password"){

                layoutVerify.visibility = View.GONE
                btnLogin.text  ="Login"
                layoutLogin.visibility = View.VISIBLE
                txtloginWithOtp.text = "Login with OTP"
                txtSkip.text = "Skip>>"

            }
        }

    }

    private fun resendOtp(username: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callResendOtp(username)
        ResponseListner(this).getResponse(call,this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 2) {
//            getLatlang()
        }
    }

    private var gpsTracker: GPSTracker? = null


    private lateinit var dialog: Dialog

    fun doLogin(view: View) {

        username = edtUsername.text.toString()
        pass = edtPass.text.toString()

        if (btnLogin.text.toString() == "Login") {
            if (username.isEmpty()|| !Utility.isValidMobile(username)) {
                dialog = Utility.createErrorDialog(
                    this@LoginActivity,
                    "Please enter valid mobile number. "
                )!!
                dialog.show()
            } else if (pass.isEmpty()) {
                dialog = Utility.createErrorDialog(
                    this@LoginActivity,
                    "Password can not be left blank."
                )!!
                dialog.show()
            } else if (!Utility.isConnectingToInternet(this@LoginActivity)) {
                dialog = Utility.createErrorDialog(
                    this@LoginActivity,
                    getString(R.string.network_error)
                )!!
                dialog.show()
            } else {
                callLogin(username, pass)
            }
        } else if (btnLogin.text.toString() == "Send OTP") {
            username = edtMobile.text.toString()
            if (/*username.isEmpty() ||*/ !Utility.isValidMobile(username)) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter valid mobile number. ",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!Utility.isConnectingToInternet(this@LoginActivity)) {
                dialog = Utility.createErrorDialog(
                    this@LoginActivity,
                    getString(R.string.network_error)
                )!!
                dialog.show()
            } else
                requestForOtp()

        } else if (btnLogin.text.toString() == "Submit OTP"){
            val otp = editOtp.text.toString()
            val username = edtMobile.text.toString()
            if (otp.isEmpty()){
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter OTP. ",
                    Toast.LENGTH_SHORT
                ).show()
            } else  if (username.isEmpty()|| !Utility.isValidMobile(username)) {
                dialog = Utility.createErrorDialog(
                    this@LoginActivity,
                    "Please enter valid mobile number. "
                )!!
                dialog.show()
            }
            else if (!Utility.isConnectingToInternet(this@LoginActivity)) {
                dialog = Utility.createErrorDialog(
                    this@LoginActivity,
                    getString(R.string.network_error)
                )!!
                dialog.show()
            } else{
                verifyOTP(otp,username)
            }
        }
    }

    private fun verifyOTP(otp: String, username: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callVerifyOtp(username, otp)
        ResponseListner(this).getResponse(call,this)
    }

    private fun callLogin(username: String, pass: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callLogin(username, pass)
        ResponseListner(this).getResponse(call,this)
    }

    fun signup(view: View) {
        val i = Intent(applicationContext, SignupActivity::class.java)
        startActivity(i, options.toBundle())
        finish()
    }

    fun skip(view: View) {
        if (txtSkip.text == "Skip>>"){
            ClsGeneral.setPreferences(
                this@LoginActivity, "skip",
                "skip"
            )
            val i = Intent(applicationContext, HomeActivity::class.java)
            startActivity(i, options.toBundle())
            finish()
        } else if (txtSkip.text == "Login With Password"){
            layoutVerify.visibility = View.GONE
            btnLogin.text  ="Login"
            layoutLogin.visibility = View.VISIBLE
            txtloginWithOtp.text = "Login with OTP"
            txtSkip.text = "Skip>>"
        }

    }

    override fun onApiResponse(response: Any?) {
        process.dialog.dismiss()
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))

        if (response != null) {

            if (response is LoginResponse) {
                val loginReponse: LoginResponse = response as LoginResponse

                if (loginReponse.Status != null && loginReponse.Status == "Success") {
                    if (loginReponse.Messege != null && loginReponse.Messege == "Login Successfully !!!") {
                        ClsGeneral.setPreferences(
                            this@LoginActivity, "username",
                            loginReponse.Data.username
                        )
                        ClsGeneral.setPreferences(
                            this@LoginActivity, "userid",
                            loginReponse.Data.Id.toString()
                        )

                        ClsGeneral.setPreferences(
                            this@LoginActivity, "skip", "")

                        val i: Intent
                        if (activtyName!=null && activtyName=="searchDetail"){
                            i = Intent(applicationContext, SearchActivity::class.java)
                            startActivity(i, options.toBundle())
                            finish()
                        } else if (activtyName!=null && activtyName=="catDetail"){
                            i = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(i, options.toBundle())
                            finish()
                        }
                        else{
                            i = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(i, options.toBundle())
                            finish()
                        }


                    } else {
                        dialog = Utility.createErrorDialog(
                            this@LoginActivity, loginReponse.Messege
                        )!!
                        dialog.show()
                    }

                } else {
                    dialog = Utility.createErrorDialog(
                        this@LoginActivity, loginReponse.Messege
                    )!!
                    dialog.show()
                }

            } else if (response is OtpVerifyResponse) {
                val otpVerifyResponse: OtpVerifyResponse = response

                if (otpVerifyResponse.Status != null && otpVerifyResponse.Status == "Success") {
                    if (otpVerifyResponse.Messege != null &&
                        (otpVerifyResponse.Messege == "OTP Send On Your Mobile Number !!!"
                                || otpVerifyResponse.Messege==("OTP Re-Send On Mobile Number !!!"))) {
                        editOtp.visibility = View.VISIBLE
                        btnLogin.text = "Submit OTP"
                        txtSkip.text = "Login With Password"
                    } else {
                        dialog = Utility.createErrorDialog(
                            this@LoginActivity, otpVerifyResponse.Messege
                        )!!
                        dialog.show()
                    }

                } else {
                    dialog = Utility.createErrorDialog(
                        this@LoginActivity, otpVerifyResponse.Messege
                    )!!
                    dialog.show()
                }
            }
        }

    }

    private fun requestForOtp() {
        process.show(this, "Please Wait...")

        val call = APIClient().apiInterface.callLoginWithOtp(username)
        ResponseListner(this).getResponse(call,this)
    }


    override fun onApiFailure(message: String?) {
        Log.d("TAG", "onApiFailure: $message")
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(
            this@LoginActivity,
            getString(R.string.error)
        )!!
        dialog.show()
    }

}
