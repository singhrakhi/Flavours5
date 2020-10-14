package com.flavours5.ui.search

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.adapters.ProductAdapter
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.AddToCartResponse
import com.flavours5.models.CartDetailsResponse
import com.flavours5.models.ProductResponse
import com.flavours5.ui.cart.CartActivity
import com.flavours5.ui.setup.LoginActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import java.lang.Exception

class SearchActivity : AppCompatActivity(), OnResponseInterface,ProductAdapter.AddToCartInterface,
    ProductAdapter.ClickData, ProductAdapter.ProductDetailInterface {

    private lateinit var imgBack: ImageView
    private lateinit var edtSearch: EditText
    private val process = Progress()
    private lateinit var dialog: Dialog
    private lateinit var options: ActivityOptions
    private lateinit var username: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var type: String
    private var cartCount: Int = 0
    private var searchKey: String=""
    private lateinit var imgCart: ImageView
    private lateinit var txtCartBadge: TextView
    private lateinit var cartSize: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar!!.hide()
        username = ClsGeneral.getPreferences(this@SearchActivity, "username").toString()
        options = ActivityOptions.makeCustomAnimation(this@SearchActivity, R.anim.up_from_bottom, R.anim.up_from_bottom)

        initView()
    }

    private fun getCartDetails(username: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartList(username)
        ResponseListner(this).getResponse(call,this)
    }

    override fun onResume() {
        super.onResume()

        if (!Utility.isConnectingToInternet(this@SearchActivity)) {
            dialog = Utility.createErrorDialog(this@SearchActivity, getString(R.string.network_error))!!
            dialog.show()
        } else {

            if (username!=null && username.isNotEmpty())
                getCartDetails(username)
        }
    }

    private fun initView() {
        imgBack = findViewById(R.id.imgBack)
        txtCartBadge = findViewById(R.id.cart_badge)
        imgCart = findViewById(R.id.imgCart)
        edtSearch = findViewById(R.id.editTxtSearch)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        imgBack.setOnClickListener {
            finish()
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(cs: CharSequence, start: Int, before: Int, count: Int) {
                if (cs != null && cs.length > 2) {
                   searchKey = cs.toString().trim { it <= ' ' }
                    if (!Utility.isConnectingToInternet(this@SearchActivity)) {
                        dialog = Utility.createErrorDialog(this@SearchActivity, getString(R.string.network_error))!!
                        dialog.show()
                    } else {
                            searchProducts(searchKey)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        imgCart.setOnClickListener {
            if (Utility.isUserLogIn(context = this)){
                val i = Intent(applicationContext, CartActivity::class.java)
                startActivity(i, options.toBundle())
            }
            else{
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("activity","searchDetail")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                startActivity(intent, options.toBundle())
            }

        }

    }

    private var productList: MutableList<ProductResponse.DataBean> = ArrayList()

    private fun setAdapter() {
        recyclerView.adapter = ProductAdapter(productList,context = this,clickData = this, addToCartInterface = this, productDetailInterface = this)
    }

    private fun searchProducts(key: String) {
//        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callSearchProducts(key)
        ResponseListner(this).getResponse(call,this)
        Log.d("TAG", "rakhi: " + Gson().toJson(call!!.request().body))

    }

    override fun onApiResponse(response: Any?) {
        try {
            process.dialog.dismiss()
        } catch (e:Exception){

        }
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))

        if (response!=null){
            if (response is ProductResponse){
                if (productList!=null)
                    productList.clear()
                if (response.status!=null && response.status=="Success"){
                    if (response.data!=null && response.data.size>0){
                        productList = response.data
                        setAdapter()
                    } else{
                        dialog = Utility.createErrorDialog(this@SearchActivity, "No Product found.")!!
                        dialog.show()
                    }
                } else{
                    val msg: String = if (response.message!=null){
                        response.message
                    } else "No product found."
                    dialog = Utility.createErrorDialog(this@SearchActivity, msg)!!
                    dialog.show()
                }
            } else if (response is AddToCartResponse){
                if (response.status!=null && response.status =="Success"){
                    if (type!=null && type == "add" && response.message!=null
                        && response.message=="Product Added Successfully !!!"){
                        layoutAdd.visibility = View.VISIBLE
                        txtAddView.visibility = View.GONE
                        cartCount++
                        txtCartBadge.text = cartCount.toString()
                        txtCartBadge.visibility = View.VISIBLE
                    } else {
                        if (response.data!=null)
                            txtCount.text = response.data.toString()
                    }
                } else{
                    dialog = Utility.createErrorDialog(
                        this@SearchActivity,
                        response.message
                    )!!
                    dialog.show()
                }
            } else if (response is CartDetailsResponse) {
                    if (response.data != null) {
                        ClsGeneral.setPreferences(this, "cartsize", response.data.size.toString())
                    } else {
                        ClsGeneral.setPreferences(this, "cartsize", "")
                    }
                cartSize = ClsGeneral.getPreferences(this@SearchActivity, "cartsize").toString()
                if (cartSize!=null && cartSize.length>0)
                    cartCount = cartSize.toInt()
                else cartCount = 0

                if (Utility.isUserLogIn(context = this)) {
                    if (cartCount > 0) {
                        txtCartBadge.text = cartSize
                        txtCartBadge.visibility = View.VISIBLE
                    } else
                        txtCartBadge.visibility = View.GONE
                } else
                    txtCartBadge.visibility = View.GONE
            }

        }
    }

    override fun onApiFailure(message: String?) {
        try {
            process.dialog.dismiss()
            Log.d("TAG", "onApiFailure: $message")

            dialog = Utility.createErrorDialog(
                this@SearchActivity,
                getString(R.string.error)
            )!!
            dialog.show()
        } catch (e:Exception){

        }
    }

    private lateinit var layoutAdd: LinearLayout
    private lateinit var txtAddView: TextView

    override fun clickAdd(i: Int, click_type: String,layout: LinearLayout,txtAdd: TextView) {
        type = click_type
        layoutAdd = layout
        txtAddView = txtAdd
        if (!Utility.isConnectingToInternet(this)) {
            dialog = Utility.createErrorDialog(
                this,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else{
            if (Utility.isUserLogIn(context = this)){
                addToCart(productList[i].productUnitId)
            }
            else{
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("activity","searchDetail")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                startActivity(intent, options.toBundle())
            }
        }
    }

    private fun addToCart(productId: Int) {
        val call = APIClient().apiInterface.callAddToCart(username ,productId)
        ResponseListner(this).getResponse(call,this)
    }

    private fun plusProductToCart(productId: Int) {
//        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartPlus(username,productId)
        ResponseListner(this).getResponse(call,this)
    }

    private fun minusProductFromCart(productId: Int) {
//        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartMinus(username,productId)
        ResponseListner(this).getResponse(call,this)
    }


    private lateinit var txtCount: TextView;

    override fun getDetails(position: Int, click_type: String, tvCount: TextView, btnAdd: TextView, updateQYT: LinearLayout) {
        var i =tvCount.text.toString().toInt()
        type = click_type
        txtCount = tvCount
        if (click_type == "plus"){
            i += 1
//            tvCount.text = i.toString()
            plusProductToCart(productList[position].productUnitId)
        }
        else if (click_type == "minus"){
            if (i>1){
                i -= 1
//                tvCount.text = i.toString()
                minusProductFromCart(productList[position].productUnitId)
            } else{
                btnAdd.visibility = View.VISIBLE
                updateQYT.visibility = View.GONE
            }

        }
    }

    override fun clickItem(i: Int, click_type: String) {

    }

}
