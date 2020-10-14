package com.flavours5.ui.order

import android.app.ActivityOptions
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.adapters.OrderedItemListAdapter
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.OrderDetailsResponse
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_details.*

class OrderDetailsActivity : AppCompatActivity(), OnResponseInterface {
    private lateinit var dialog: Dialog
    private val process = Progress()
    private lateinit var username: String
    private lateinit var options: ActivityOptions
    private lateinit var recyclerView: RecyclerView
    private var txtMRP_cart: TextView? = null
    private var txtDiscountAmount: TextView? = null
    private var txtDeliveryCharges: TextView? = null
    private var txtSubTotal: TextView? = null
    var orderID: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)

        (v.findViewById(R.id.title) as TextView).text = "Order Details"

        supportActionBar?.customView = v

        options =
            ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        username = ClsGeneral.getPreferences(this@OrderDetailsActivity, "username").toString()

        initView()

    }

    private fun initView() {

        recyclerView = findViewById(R.id.recycler_view_ordered_item)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        txtMRP_cart = findViewById(R.id.txtMRP_cart)
        txtDiscountAmount = findViewById(R.id.txtDiscountAmount)
        txtDeliveryCharges = findViewById(R.id.txtDeliveryCharges)
        txtSubTotal = findViewById(R.id.txtSubTotal)

        orderID = intent.getStringExtra("ID")
        var deliveryCharge = intent.getStringExtra("deliveryCharge")
        if (deliveryCharge != null && deliveryCharge.length > 0) txtDeliveryCharges!!.text =
            getString(R.string.rupees_symbol) + deliveryCharge else {
            deliveryCharge = "0"
            txtDeliveryCharges!!.text = "Free"
        }
        val total = intent.getStringExtra("Total").toDouble()
        val productPrice = total - deliveryCharge.toDouble()
        txtDiscountAmount!!.text = getString(R.string.rupees_symbol) + productPrice

        txtSubTotal!!.text = getString(R.string.rupees_symbol) + total
        layoutEmpty.visibility = View.GONE
        getOrderDetails(orderID!!)
    }

    private fun setAdapter(data: MutableList<OrderDetailsResponse.DataBean>) {
        val myOrdersAdapter = OrderedItemListAdapter(applicationContext, data)
        recyclerView.adapter = myOrdersAdapter
        recyclerView.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
    }

    private fun getOrderDetails(orderId: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callOrderDetails(orderId)
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

    override fun onApiResponse(response: Any?) {
        process.dialog.dismiss()
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        if (response != null) {
            if (response is OrderDetailsResponse) {
                if (response.status != null && response.status == "Success") {
                    if (response.data != null && response.data.size > 0) {
                        setAdapter(response.data)
                    }    else
                        layoutEmpty.visibility = View.VISIBLE

                } else {
                    layoutEmpty.visibility = View.VISIBLE
                }
            } else {

                layoutEmpty.visibility = View.VISIBLE
            }
        }

    }

    override fun onApiFailure(message: String?) {
        Log.d("TAG", "onApiFailure: $message")
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(this@OrderDetailsActivity, getString(R.string.error))!!
        dialog.show()
    }

}
