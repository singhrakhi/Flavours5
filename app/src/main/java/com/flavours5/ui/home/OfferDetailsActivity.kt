
package com.flavours5.ui.home

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
import com.flavours5.models.SubCategoryResponse
import com.flavours5.ui.cart.CartActivity
import com.flavours5.ui.search.SearchActivity
import com.flavours5.ui.setup.LoginActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.custom_toolbase.*
import java.lang.Exception

class OfferDetailsActivity : AppCompatActivity(), OnResponseInterface, ProductAdapter.ClickData,
    ProductAdapter.AddToCartInterface, ProductAdapter.ProductDetailInterface {
    private lateinit var dialog: Dialog
    private lateinit var recyclerView: RecyclerView
    private val process = Progress()
    private lateinit var txtTitle: TextView
    private lateinit var options: ActivityOptions
    private lateinit var txtCartBadge: TextView
    private var subCatListAll: MutableList<SubCategoryResponse.DataBean> = ArrayList()
    private lateinit var username: String
    private lateinit var cartSize: String
    private lateinit var type: String
    private var subcatid: String = ""
    private var catid: String = ""
    private var cartCount: Int = 0
    private lateinit var imgCart: ImageView
    private lateinit var imgSearch: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_details)

        supportActionBar!!.hide()
        options =
            ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        username = ClsGeneral.getPreferences(this@OfferDetailsActivity, "username").toString()

        catid = intent.getStringExtra("catId")
        subcatid = intent.getStringExtra("subcatid")

        initView()

        if (!Utility.isConnectingToInternet(this)) {
            dialog = Utility.createErrorDialog(
                this,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            if (subcatid!=null && subcatid.length>0){
                getProducts(subcatid.toInt())
            } else{
                getSubCategoryData(catid.toInt())
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (username!=null && username.length>0)
        getCartDetails(username)




    }

    private fun initView() {
        txtTitle = findViewById(R.id.txtTitle)
        txtCartBadge = findViewById(R.id.cart_badge)
        imgCart = findViewById(R.id.imgCart)
        imgSearch = findViewById(R.id.imgSearch)
        if (Utility.isUserLogIn(context = this)) {
            if (cartCount > 0) {
                txtCartBadge.text = cartSize
                txtCartBadge.visibility = View.VISIBLE
            } else
                txtCartBadge.visibility = View.GONE
        } else
            txtCartBadge.visibility = View.GONE

        txtTitle.text = "Offers"
        recyclerView = findViewById(R.id.productsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        imgCart.setOnClickListener {

            if (Utility.isUserLogIn(context = this)) {
                val i = Intent(applicationContext, CartActivity::class.java)
                startActivity(i, options.toBundle())
            } else {
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                val i = Intent(applicationContext, LoginActivity::class.java)
                i.putExtra("activity", "productDetail")
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i, options.toBundle())
                finish()
            }
        }

        imgSearch.setOnClickListener {
            val i = Intent(applicationContext, SearchActivity::class.java)
            startActivity(i, options.toBundle())
        }


    }

    private fun getSubCategoryData(id: Int) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callSubCat(id)
        ResponseListner(this).getResponse(call,this)
    }

    private fun getProducts(subCatId: Int) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callProducts(subCatId)
        ResponseListner(this).getResponse(call,this)
    }
    private var productList: MutableList<ProductResponse.DataBean> = ArrayList()

    private fun setAdapter() {
        recyclerView.adapter =
            ProductAdapter(productList, context = this, clickData = this, addToCartInterface = this, productDetailInterface = this)
    }

    private fun getCartDetails(username: String) {
//        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartList(username)
        ResponseListner(this).getResponse(call,this)
    }

    private lateinit var txtCount: TextView;

    override fun onApiResponse(response: Any?) {
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
        process.dialog.dismiss()

        if (response != null) {
            if (response is ProductResponse) {
                if (productList!=null)
                    productList.clear()
                if (response.status != null && response.status == "Success") {
                    if (response.data != null && response.data.size > 0) {
                        productList = response.data
                        setAdapter()
                    } else {
                        dialog =
                            Utility.createErrorDialog(this, "No Product found.")!!
                        dialog.show()
                    }
                } else {
                    val msg: String = if (response.message != null) {
                        response.message
                    } else
                        "No product found."
                    dialog = Utility.createErrorDialog(
                        this,
                        msg
                    )!!
                    dialog.show()
                }
            } else if (response is AddToCartResponse) {
                if (response.status != null && response.status == "Success") {
                    if (type != null && type == "add" && response.message != null && response.message == "Product Added Successfully !!!") {
                        layoutAdd.visibility = View.VISIBLE
                        txtAddView.visibility = View.GONE
                        cartCount++
                        txtCartBadge.text = cartCount.toString()
                        txtCartBadge.visibility = View.VISIBLE
                    } else {
                        if (response.data != null)
                            txtCount.text = response.data.toString()
                    }
                } else {
                    dialog = Utility.createErrorDialog(
                        this,
                        response.message
                    )!!
                    dialog.show()
                }
            } else  if (response is SubCategoryResponse) {
                if (response.data != null) {
                    if (response.data != null && response.data.size > 0) {
                        if (subCatListAll!=null)
                            subCatListAll.clear()
                        subCatListAll = response.data
                        getProducts(subCatListAll[0].id)
                    }
                }
            } else if (response is CartDetailsResponse) {
                if (response.data != null) {
                    ClsGeneral.setPreferences(this, "cartsize", response.data.size.toString())

                } else {
                    ClsGeneral.setPreferences(this, "cartsize", "")
                }

                cartSize = ClsGeneral.getPreferences(this, "cartsize").toString()
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
        Log.d("TAG", "onApiFailure: $message")
       try {
           process.dialog.dismiss()
           dialog = Utility.createErrorDialog(this, getString(R.string.error))!!
           dialog.show()
       } catch (e: Exception){

       }
    }

    override fun getDetails(
        position: Int, click_type: String, tvCount: TextView, btnAdd: TextView,
        updateQYT: LinearLayout
    ) {
        var i = tvCount.text.toString().toInt()
        type = click_type
        txtCount = tvCount
        if (click_type == "plus") {
            i += 1
//            tvCount.text = i.toString()
            plusProductToCart(productList[position].productUnitId)
        } else if (click_type == "minus") {
            if (i > 1) {
                i -= 1
//                tvCount.text = i.toString()
                minusProductFromCart(productList[position].productUnitId)
            } else {
                btnAdd.visibility = View.VISIBLE
                updateQYT.visibility = View.GONE
            }
        }
    }

    private fun plusProductToCart(productId: Int) {
//        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartPlus(username, productId)
        ResponseListner(this).getResponse(call,this)
    }

    private fun minusProductFromCart(productId: Int) {
//        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartMinus(username, productId)
        ResponseListner(this).getResponse(call,this)
    }

    private lateinit var layoutAdd: LinearLayout

    private lateinit var txtAddView: TextView

    override fun clickAdd(i: Int, click_type: String,layout: LinearLayout,txtAdd: TextView) {
        type = click_type
        txtAddView = txtAdd
        layoutAdd = layout
        if (!Utility.isConnectingToInternet(this)) {
            dialog = Utility.createErrorDialog(
                this,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            if (Utility.isUserLogIn(context = this))
                addToCart(productList[i].productUnitId)
            else {
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("activity", "productDetail")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent, options.toBundle())
            }
        }
    }

    private fun addToCart(productId: Int) {
        val call = APIClient().apiInterface.callAddToCart(username, productId)
        ResponseListner(this).getResponse(call,this)
    }

    override fun clickItem(i: Int, click_type: String) {

    }

}
