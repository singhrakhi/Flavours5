package com.flavours5.ui.category

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.adapters.ProductAdapter
import com.flavours5.adapters.SubCategoryAdapter
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.AddToCartResponse
import com.flavours5.models.CategoryResponse
import com.flavours5.models.ProductResponse
import com.flavours5.models.SubCategoryResponse
import com.flavours5.ui.cart.CartActivity
import com.flavours5.ui.prodcuts.ProdcutDetailsActivity
import com.flavours5.ui.prodcuts.ProductsActivity
import com.flavours5.ui.search.SearchActivity
import com.flavours5.ui.setup.LoginActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CategoryDetailsActivity : AppCompatActivity(), OnResponseInterface,
    SubCategoryAdapter.CateClickInterface, ProductAdapter.ClickData, ProductAdapter.ProductDetailInterface,
    ProductAdapter.AddToCartInterface {
    private lateinit var imgBack: LinearLayout
    private val process = Progress()
    private lateinit var dialog: Dialog
    private val requests = mutableMapOf<Int, Boolean>()
    private lateinit var spinnerCategory: Spinner
    private lateinit var imgSearch: ImageView
    private lateinit var imgCart: ImageView
    private lateinit var username: String
    private lateinit var cartSize: String
    private lateinit var txtBadges: TextView
    private lateinit var txtCategory: TextView
    private lateinit var catId: String
    private var spinnerStatus: Boolean = false
    private lateinit var options: ActivityOptions
    private var type: String = ""
    private var cartCount: Int = 0
    private var categoryName: String = ""
    private var categoryList: MutableList<CategoryResponse.DataBean> = ArrayList()
    private var categoryArray = ArrayList<String>()
    private var categoryIdArray = ArrayList<String>()
    private var statusArrayList = ArrayList<String>()
    private var subCatListAll: MutableList<SubCategoryResponse.DataBean> = ArrayList()
    private lateinit var recyclerViewSubCat: RecyclerView
    private lateinit var recyclerProduct: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_details)
        supportActionBar!!.hide()
        val gson = Gson()
        val jsonOutput = intent.getStringExtra("categoryList")
        val listType: Type = object : TypeToken<List<CategoryResponse.DataBean?>?>() {}.type
        val posts: MutableList<CategoryResponse.DataBean> =
            gson.fromJson<MutableList<CategoryResponse.DataBean>>(jsonOutput, listType)
        categoryList = posts
        catId = intent.getStringExtra("catId")
        username = ClsGeneral.getPreferences(this@CategoryDetailsActivity, "username").toString()
        cartSize = ClsGeneral.getPreferences(this@CategoryDetailsActivity, "cartsize").toString()
        cartCount = if (cartSize != null && cartSize.length > 0)
            cartSize.toInt()
        else 0
        options =
            ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)

        initView()
    }

    private fun initView() {
        imgBack = findViewById(R.id.img_back_icon)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        imgSearch = findViewById(R.id.imgSearch)
        imgCart = findViewById(R.id.imgCart)
        txtBadges = findViewById(R.id.cart_badge)
        txtCategory = findViewById(R.id.txtCategory)
        recyclerViewSubCat = findViewById(R.id.recycler_view)
        recyclerViewSubCat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerProduct = findViewById(R.id.recycler_product)
        recyclerProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        imgBack.setOnClickListener {
            finish()
        }

        if (Utility.isUserLogIn(context = this)) {
            if (cartCount > 0) {
                txtBadges.text = cartSize
                txtBadges.visibility = View.VISIBLE
            } else
                txtBadges.visibility = View.GONE
        } else
            txtBadges.visibility = View.GONE

        imgCart.setOnClickListener {
            if (Utility.isUserLogIn(context = this)) {
                val i = Intent(applicationContext, CartActivity::class.java)
                startActivity(i, options.toBundle())

            } else {
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                val i = Intent(applicationContext, LoginActivity::class.java)
                i.putExtra("activity", "catDetail")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                startActivity(i, options.toBundle())
            }
        }

        imgSearch.setOnClickListener {
            val i = Intent(applicationContext, SearchActivity::class.java)
            startActivity(i, options.toBundle())
        }

        for (x in categoryList) {
            categoryArray.add(x.categoryname)
            categoryIdArray.add(x.id.toString())
        }

        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
            this, android.R.layout.simple_spinner_item,
            categoryArray as List<String?>
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
        txtCategory.text = intent.getStringExtra("catName")

        spinnerCategory.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int,
                id: Long
            ) {
              try{
                  if (spinnerStatus) {
                      categoryName = categoryArray[position]
                      txtCategory.text = categoryArray[position]
                      getSubCategoryData(categoryIdArray[position].toInt())
                  } else {
                      spinnerStatus = true
                  }
              } catch (e:Exception){}
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        getSubCategoryData(catId.toInt())

    }

    private lateinit var txtCount: TextView;

    override fun onApiResponse(response: Any?) {

        if (response != null) {
            Log.d("TAG", "onApiResponse: " + Gson().toJson(response))

            if (response is SubCategoryResponse) {
                if (response.data != null) {
                    if (response.data != null && response.data.size > 0) {
                        subCatListAll = response.data
                        setAdapter(subCatListAll)
                        getProducts(subCatListAll[0].id)
                    }
                }
            } else if (response is ProductResponse) {
                if (process.dialog != null)
                    process.dialog.dismiss()
                productList = ArrayList()
                if (response.status != null && response.status == "Success") {
                    if (response.data != null && response.data.size > 0) {
                        productList = response.data
                    } else {
                        productList.clear()

                        dialog = Utility.createErrorDialog(
                            this@CategoryDetailsActivity,
                            "No Product found."
                        )!!
                        dialog.show()
                    }
                } else {
                    val msg: String = if (response.message != null) {
                        response.message
                    } else
                        "No product found."
                    dialog = Utility.createErrorDialog(
                        this@CategoryDetailsActivity,
                        msg
                    )!!
                    dialog.show()
                }

                setProductAdapter()

            } else if (response is AddToCartResponse) {
                if (process.dialog != null)
                    process.dialog.dismiss()
                if (response.status != null && response.status == "Success") {
                    if (type != null && type == "add" && response.message != null && response.message == "Product Added Successfully !!!") {
                        layoutAdd.visibility = View.VISIBLE
                        txtAddView.visibility = View.GONE
                        cartCount++
                        txtBadges.text = cartCount.toString()
                        txtBadges.visibility = View.VISIBLE
                    } else {
                        if (response.data != null)
                            txtCount.text = response.data.toString()
                    }
                } else {
                    dialog = Utility.createErrorDialog(
                        this@CategoryDetailsActivity,
                        response.message
                    )!!
                    dialog.show()
                }
            }
        }
    }



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


    private lateinit var productList: MutableList<ProductResponse.DataBean>

    private fun setProductAdapter() {
        val adapter =  ProductAdapter(productList, context = this, clickData = this, addToCartInterface = this, productDetailInterface = this)
        recyclerProduct.adapter = adapter

        if (adapter!=null)
            adapter.notifyDataSetChanged()
    }


    private fun setAdapter(subCatListAll: MutableList<SubCategoryResponse.DataBean>) {
        recyclerViewSubCat.adapter = SubCategoryAdapter(this, subCatListAll, this)
    }

    private fun getSubCategoryData(id: Int) {
//        process.dialog.show()
        val call = APIClient().apiInterface.callSubCat(id)
        ResponseListner(this).getResponse(call,this@CategoryDetailsActivity)
    }

    override fun onApiFailure(message: String?) {

        try {
            if (process.dialog != null)
                process.dialog.dismiss()
        } catch (e: Exception) {

        }
        dialog = Utility.createErrorDialog(this, getString(R.string.error))!!
        dialog.show()
    }

    override fun subCatClick(position: Int) {
        if (!Utility.isConnectingToInternet(this@CategoryDetailsActivity)) {
            dialog = Utility.createErrorDialog(
                this@CategoryDetailsActivity,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            getProducts(subCatListAll[position].id)
        }
    }

    private fun getProducts(subCatId: Int) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callProducts(subCatId)
        ResponseListner(this).getResponse(call,this@CategoryDetailsActivity)
    }

    private fun addToCart(productId: Int) {
        val call = APIClient().apiInterface.callAddToCart(username, productId)
        ResponseListner(this).getResponse(call,this@CategoryDetailsActivity)
    }
    private lateinit var layoutAdd: LinearLayout
    private lateinit var txtAddView: TextView

    override fun clickAdd(i: Int, click_type: String,layout: LinearLayout,txtAdd: TextView) {
        type = click_type
        layoutAdd = layout
        txtAddView = txtAdd
        if (!Utility.isConnectingToInternet(this@CategoryDetailsActivity)) {
            dialog = Utility.createErrorDialog(
                this@CategoryDetailsActivity,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            if (Utility.isUserLogIn(context = this)) {
                addToCart(productList[i].productUnitId)
            } else {
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.putExtra("activity", "catDetail")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                startActivity(intent, options.toBundle())
            }
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
            tvCount.text = i.toString()
            plusProductToCart(productList[position].productUnitId)
        } else if (click_type == "minus") {
            if (i > 1) {
                i -= 1
                tvCount.text = i.toString()
                minusProductFromCart(productList[position].productUnitId)
            } else {
                btnAdd.visibility = View.VISIBLE
                updateQYT.visibility = View.GONE
            }
        }
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



    override fun clickItem(i: Int, click_type: String) {

//        val intent = Intent(applicationContext, ProdcutDetailsActivity::class.java)
//        intent.putExtra("productModel", Gson().toJson(productList[i]))
//        startActivity(intent, options.toBundle())

    }


}
