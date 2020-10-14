package com.flavours5.ui.prodcuts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flavours5.R

class ProdcutDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prodcut_details)

        supportActionBar!!.hide()
    }
}
