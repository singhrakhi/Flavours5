package com.flavours5.ui.account

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.ui.address.MyAddressActivity
import com.flavours5.ui.home.BookGroceryTruckActivity

class AccountFragment : Fragment() {
    private lateinit var txtUser: TextView
    private lateinit var txtCurrentAdd: TextView
    private lateinit var imgLogo: ImageView
    private lateinit var txtAdd: TextView
    private lateinit var options: ActivityOptions
    private lateinit var txtChange: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_account, container, false)
            imgLogo = root.findViewById(R.id.logo)
            txtUser = root.findViewById(R.id.txtUserMobile)
            txtCurrentAdd = root.findViewById(R.id.txtUserCurrentAdd)
        options =
            ActivityOptions.makeCustomAnimation(context, R.anim.up_from_bottom, R.anim.up_from_bottom)
        val username = ClsGeneral.getPreferences(context, "username").toString()
        txtUser.text = username
        val currentAddress = ClsGeneral.getPreferences(context, "currentAddress").toString()

        txtAdd = root.findViewById(R.id.txtAdd)
        txtCurrentAdd.text = currentAddress

        Glide.with(this)
            .asBitmap()
            .load(R.drawable.logo)
            .apply(RequestOptions.circleCropTransform())
            .into(imgLogo)

        txtAdd.setOnClickListener {
            val i = Intent(context, MyAddressActivity::class.java)
            startActivity(i, options.toBundle())
        }

        txtChange = root.findViewById(R.id.txtChange)

        txtChange.setOnClickListener {
            val i = Intent(context, ChangePasswordActivity::class.java)
            startActivity(i, options.toBundle())
        }

        return root
    }

}
