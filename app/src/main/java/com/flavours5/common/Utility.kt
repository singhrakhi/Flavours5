package com.flavours5.common

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.flavours5.R
import java.io.*
import java.util.regex.Pattern


object Utility {

    public fun isUserLogIn(context: Context): Boolean {

        val username: String = ClsGeneral.getPreferences(context, "username").toString()
        if (username!=null && username.length>0)
            return true
        else return false
    }


    fun createErrorDialog(context: Context?,  msg: String?): Dialog? {
       val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.error_alert_dialog)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        val txtOk: TextView = dialog.findViewById(R.id.dialog_error_ok)
        val txtMsg: TextView = dialog.findViewById(R.id.dialog_error_msg)
        val finalDialog: Dialog? = dialog
        txtOk.setOnClickListener { view: View? -> finalDialog!!.dismiss() }
        txtMsg.text = msg

        return dialog
    }

    fun isConnectingToInternet(context: Context?): Boolean {
        val connectivity = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        for (i in info.indices) {
            if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }


    fun showAlertActivity(message: String?, context: Activity?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, id -> }
        builder.show()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidMobile(phone: String): Boolean {
        var check = false
        check = if (!Pattern.matches("[a-zA-Z]+", phone)) {
            !(phone.length < 6 || phone.length > 13)
        } else {
            false
        }
        return check
    }


    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }


    private fun capitalize(str: String): String? {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true
        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }
        return phrase.toString()
    }

    fun readData(): String {
        val filename = "/iNest/mttext.txt"
        var aDataRow: String
        var aBuffer = ""
        try {
            val myFile = File("/sdcard/$filename")
            val fIn = FileInputStream(myFile)
            val myReader = BufferedReader(
                InputStreamReader(fIn)
            )

            try {
                while (myReader.readLine().also { aDataRow = it } != null) {
                    aBuffer += aDataRow + "\n"
                }
                myReader.close()
            } catch (e: java.lang.Exception){
                Log.d("fileException : ",e.message)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return aBuffer.trim { it <= ' ' }
    }

}