package com.flavours5.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.flavours5.R
import kotlinx.android.synthetic.main.activity_support.*

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)

        (v.findViewById(R.id.title) as TextView).text = "Support"

        supportActionBar?.customView = v

        initView()
    }

    private fun initView() {
        Glide.with(this)
            .asBitmap()
            .load(R.drawable.logo)
            .apply(RequestOptions.circleCropTransform())
            .into(imgLogo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
