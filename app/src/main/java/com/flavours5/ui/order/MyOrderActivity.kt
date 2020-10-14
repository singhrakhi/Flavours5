package com.flavours5.ui.order

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
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
import com.flavours5.adapters.MyOrdersAdapter
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.MyOrderResponse
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_my_order.*
import java.util.*

class MyOrderActivity : AppCompatActivity(), OnResponseInterface,MyOrdersAdapter.ViewDetailsInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dialog: Dialog
    private val process = Progress()
    private lateinit var username: String
    private lateinit var options: ActivityOptions
    private var orderModelArrayList: MutableList<MyOrderResponse.DataBean?>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)

        (v.findViewById(R.id.title) as TextView).text = "My Order"

        supportActionBar?.customView = v

        initView()

    }

    private fun initView() {
        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        username = ClsGeneral.getPreferences(this@MyOrderActivity, "username").toString()

        recyclerView= findViewById(R.id.recycler_view_order)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (!Utility.isConnectingToInternet(this)) {
            dialog = Utility.createErrorDialog(this, getString(R.string.network_error))!!
            dialog.show()
        } else {
            getMyOrder(username)
        }
    }

    private fun setAdapter() {
        orderModelArrayList!!.reverse()
        val myOrdersAdapter = MyOrdersAdapter(applicationContext, orderModelArrayList, this)
        recyclerView.adapter = myOrdersAdapter
        recyclerView.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getMyOrder(username: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callMyOrder(username)
        ResponseListner(this).getResponse(call,this)
    }


    override fun onApiResponse(response: Any?) {
        process.dialog.dismiss()
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        if (response != null) {

            if (response is MyOrderResponse){
                if (response.status!=null && response.status == "Success"){
                    if (response.data!=null && response.data.size>0){
                        orderModelArrayList = response.data
                        setAdapter()
                    }
                } else{
                    process.dialog.dismiss()
                    dialog = Utility.createErrorDialog(this@MyOrderActivity,response.message)!!
                    dialog.show()
                }
            }
        }

    }

    override fun onApiFailure(message: String?) {
        Log.d("TAG", "onApiFailure: $message")
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(this@MyOrderActivity, getString(R.string.error))!!
        dialog.show()
    }

    override fun viewDetails(pos: Int) {
        val i = Intent(this, OrderDetailsActivity::class.java)
        i.putExtra("ID",(orderModelArrayList!![pos]!!.orderId))
        i.putExtra("deliveryCharge", orderModelArrayList!![pos]!!.deliveryCharge.toString())
        i.putExtra("Total", java.lang.String.valueOf(orderModelArrayList!![pos]!!.orderTotal))
        i.putExtra("Payable", java.lang.String.valueOf(orderModelArrayList!![pos]!!.pendingamount))
        startActivity(i,options.toBundle())
    }

}
