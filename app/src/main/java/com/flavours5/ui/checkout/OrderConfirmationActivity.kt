package com.flavours5.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.flavours5.R
import com.flavours5.ui.main.HomeActivity
import com.flavours5.ui.order.MyOrderActivity

class OrderConfirmationActivity : AppCompatActivity() {
    private lateinit var tvOrderId: TextView
    private lateinit var tvOrderDate: TextView
    private lateinit var tvContactNum: TextView
    private lateinit var tvAddress: TextView
    private lateinit var llGoHomePage: LinearLayout
    private lateinit var llTrackOrder: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)


        val deliveryDate = intent.getStringExtra("Date")
        val orderId = intent.getStringExtra("ProductId")
        val address = intent.getStringExtra("address")
        val number = intent.getStringExtra("number")


        initView()

        tvOrderId.text = orderId
        tvAddress.text = address
        tvOrderDate.text = deliveryDate
        tvContactNum.text = number
    }

    private fun initView() {
        tvOrderId = findViewById(R.id.value_order_id)
        tvOrderDate = findViewById(R.id.value_order_date)
        tvContactNum = findViewById(R.id.value_contactNum)
        tvAddress = findViewById(R.id.value_address)

        llGoHomePage = findViewById(R.id.ll_homePage)
        llTrackOrder = findViewById(R.id.ll_trackOrder)

        llGoHomePage.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        })
        llTrackOrder.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MyOrderActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        })
    }
}
