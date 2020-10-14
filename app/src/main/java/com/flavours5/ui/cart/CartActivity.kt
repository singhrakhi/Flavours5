package com.flavours5.ui.cart

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.adapters.MyCartAdapter
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.AddToCartResponse
import com.flavours5.models.CartDetailsResponse
import com.flavours5.models.DeliveryChargeData
import com.flavours5.ui.checkout.CheckOutActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import retrofit2.Call

class CartActivity : AppCompatActivity(), OnResponseInterface, MyCartAdapter.CartInterface {
    private val process = Progress()
    private lateinit var dialog: Dialog
    private lateinit var username: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtDeliveryCharges: TextView
    private lateinit var txtMRP_cart: TextView
    private lateinit var txtSubTotal: TextView
    private lateinit var txtDiscountAmount: TextView
    private lateinit var txtFreedel: TextView
    private lateinit var cartList: MutableList<CartDetailsResponse.DataBean>
   private var actualAmount = 0.0
    private lateinit var options: ActivityOptions

    private var strTotalPayAbleAmount = 0.0
    private var deliveryCharge:Double = 0.0
   private var minOrderValue:Double = 0.0
    private var total = 0.0
    private lateinit var layoutScrollView:LinearLayout
    private lateinit var txtEmpty: TextView
    private var maxOrdeValue:Double = 0.0
    private lateinit var call:Call<AddToCartResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        cartList = ArrayList()
        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)

        (v.findViewById(R.id.title) as TextView).text = "My Cart"

        supportActionBar?.customView = v

        initView()
    }

    private fun initView() {
        username = ClsGeneral.getPreferences(this@CartActivity, "username").toString()
        recyclerView = findViewById(R.id.rcCart)
        txtDeliveryCharges = findViewById(R.id.txtDeliveryCharges)
        txtFreedel = findViewById(R.id.txtFreeDel)
        txtMRP_cart = findViewById(R.id.txtMRP_cart)
        layoutScrollView = findViewById(R.id.layoutScrollView)
        txtEmpty = findViewById(R.id.layoutEmpty)
        txtSubTotal = findViewById(R.id.txtSubTotal)
        txtDiscountAmount = findViewById(R.id.txtDiscountAmount)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        layoutScrollView.visibility = View.GONE

    }

    override fun onResume() {
        super.onResume()

        if (!Utility.isConnectingToInternet(this)) {
            dialog = Utility.createErrorDialog(
                this,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            val isUpdate: Boolean = false
            getCartDetails(username, isUpdate)
            getDeliveryCharge()
        }
    }

    private fun getCartDetails(username: String, update: Boolean) {
        if (!update)
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartList(username)
        ResponseListner(this).getResponse(call,this)
    }

    private fun getDeliveryCharge() {
        val call = APIClient().apiInterface.getDeliveryCharge()
        ResponseListner(this).getResponse(call,this)
    }

    override fun onApiResponse(response: Any?) {
        try {
            if (process.dialog.isShowing){
                process.dialog.dismiss()
            }
        } catch (e: Exception){

        }

        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        val inflator = LayoutInflater.from(this)
        val v: View = inflator.inflate(R.layout.titleview, null)

        if (response != null) {
            if (response is CartDetailsResponse) {
                if (response.data != null && response.data.size > 0) {
                    (v.findViewById(R.id.title) as TextView).text=("My cart ("+response.data.size.toString()+")")
                    supportActionBar?.customView = v
                    cartList = response.data
                    txtEmpty.visibility = View.GONE
                    layoutScrollView.visibility = View.VISIBLE
                    setAdapter(cartList)
                } else {
                    (v.findViewById(R.id.title) as TextView).text=("My cart ")
                    supportActionBar?.customView = v
                    if (cartList!=null)
                    cartList.clear()
                   txtEmpty.visibility = View.VISIBLE
                    layoutScrollView.visibility = View.GONE
                }
                calculatePrice()

            } else if (response is DeliveryChargeData){
                if (response.status!=null && response.status == "Success") {
                    minOrderValue = response.data.minOrderValue
                    maxOrdeValue = response.data.shipingmoney.toDouble()
                    deliveryCharge = response.data.deliveryCharge
                    if (cartList!=null && cartList.size>0){
                        calculatePrice()
                    }
//                    if (total < maxOrdeValue)
//                        txtDeliveryCharges.text = getString(R.string.rupees_symbol) + deliveryCharge
                    txtFreedel.text = "For free delivery you have to add " + getString(R.string.rupees_symbol) + maxOrdeValue.toString() + " amount in your cart."
                } else {
                    dialog = Utility.createErrorDialog(
                        this@CartActivity, response.message
                    )!!
                    dialog.show()
                }
            } else if (response is AddToCartResponse){

                isResponse = true

                if (response.status!=null && response.status =="Success"){


//                    dialog = Utility.createErrorDialog(
//                        this@CartActivity,
//                        response.message
//                    )!!
//                    dialog.show()
                } else{
                    dialog = Utility.createErrorDialog(
                        this@CartActivity,
                        response.message
                    )!!
                    dialog.show()
                }

                val isUpdate: Boolean = true
                getCartDetails(username, isUpdate)
            }
        }
    }

    private fun setAdapter(cartList: List<CartDetailsResponse.DataBean>) {
        recyclerView.adapter = MyCartAdapter(this,cartList,this)
    }
    var deliveryChargeAfterCalc = 0.0

    @SuppressLint("SetTextI18n")
    private fun calculatePrice() {
        var discountedPrice = 0.0
        var price = 0.0
        var mrpPrice = 0.0
        if (cartList!=null && cartList.size>0){
            for (i in cartList.indices) {
                mrpPrice += cartList[i].price *
                        cartList[i].quantity
                discountedPrice += cartList[i]
                    .discountedPrice * cartList[i].quantity
                price += cartList[i].price * cartList[i].quantity
            }
            val disAmt = mrpPrice - discountedPrice
            total = discountedPrice
            actualAmount = discountedPrice
            txtMRP_cart.text = getString(R.string.rupees_symbol) + discountedPrice
            txtDiscountAmount.text = "-" + getString(R.string.rupees_symbol) + disAmt
            txtDiscountAmount.visibility = View.GONE

            if (total > maxOrdeValue) {
                txtDeliveryCharges.text = "FREE"
                deliveryChargeAfterCalc = 0.0
                txtDeliveryCharges.setTextColor(resources.getColor(R.color.green))
            } else {
                txtDeliveryCharges.setTextColor(resources.getColor(R.color.colorGrey))
                deliveryChargeAfterCalc = deliveryCharge
                txtDeliveryCharges.text = getString(R.string.rupees_symbol) + deliveryCharge
            }
            txtSubTotal.text = getString(R.string.rupees_symbol) + (discountedPrice + deliveryChargeAfterCalc)
            strTotalPayAbleAmount = discountedPrice + deliveryChargeAfterCalc
        }
    }

    private val requests = mutableMapOf<Int, Boolean>()


    override fun onApiFailure(message: String?, requestId: Int) {
        super.onApiFailure(message, requestId)
        if (this.requests[requestId] != null)
            this.requests[requestId] = false
    }

    override fun onApiResponse(response: Any?, requestId: Int) {
        super.onApiResponse(response, requestId)
        if (this.requests[requestId] != null)
            this.requests[requestId] = false
    }

    private fun plusProductToCart(productId: Int) {
//        process.show(this, "Please Wait...")

        if (this.requests[productId] == true)
            return

        this.requests[productId] = true
        val call = APIClient().apiInterface.callCartPlus(username, productId)
        ResponseListner(this).getResponse(call, this, productId)
    }

    private fun minusProductFromCart(productId: Int) {
//        process.show(this, "Please Wait...")
        if (this.requests[productId] == true)
            return

        this.requests[productId] = true
        val call = APIClient().apiInterface.callCartMinus(username, productId)
        ResponseListner(this).getResponse(call, this, productId)
    }



    override fun onApiFailure(message: String?) {
        try{
            process.dialog.dismiss()
            dialog = Utility.createErrorDialog(
                this@CartActivity,
                getString(R.string.error)
            )!!
            dialog.show()
        } catch (e: Exception){

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    lateinit var btnUpdate1: TextView

    private var isResponse = true

    override fun cartInterfaceMethod(position: Int, click_type: String?, btnUpdate: TextView) {
        if (click_type == "plus") {
                plusProductToCart(cartList[position].productUnitId)
                isResponse = false
        } else if (click_type == "minus") {
                minusProductFromCart(cartList[position].productUnitId)
                isResponse = false
        }
    }

    fun btnCheckout(view: View) {
        if (total > minOrderValue) {
            val i = Intent(this@CartActivity, CheckOutActivity::class.java)
            i.putExtra("total", (strTotalPayAbleAmount))
            i.putExtra("DeliveryCharge", deliveryChargeAfterCalc)
            i.putExtra("Discount", "")
            startActivity(i, options.toBundle())
        } else {
            Toast.makeText(this, "You have to add minimum order value is"+ getString(R.string.rupees_symbol) + minOrderValue, Toast.LENGTH_SHORT).show()
        }
    }

}
