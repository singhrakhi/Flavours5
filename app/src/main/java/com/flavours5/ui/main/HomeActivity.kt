package com.flavours5.ui.main

import android.Manifest
import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.flavours5.BuildConfig
import com.flavours5.R
import com.flavours5.common.*
import com.flavours5.models.CartDetailsResponse
import com.flavours5.ui.account.AccountFragment
import com.flavours5.ui.address.MyAddressActivity
import com.flavours5.ui.cart.CartActivity
import com.flavours5.ui.category.CategoryFragment
import com.flavours5.ui.home.AboutActivity
import com.flavours5.ui.home.BookGroceryTruckActivity
import com.flavours5.ui.home.HomeFragment
import com.flavours5.ui.home.SupportActivity
import com.flavours5.ui.order.MyOrderActivity
import com.flavours5.ui.search.SearchActivity
import com.flavours5.ui.setup.LoginActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import java.io.IOException

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener, OnResponseInterface {
    private lateinit var imgHome: ImageView
    private lateinit var imgCat: ImageView
    private lateinit var imgOrder: ImageView
    private lateinit var imgAccount: ImageView
    private lateinit var currentAddress: String
    private lateinit var txtHome: TextView
    private lateinit var txtCat: TextView
    private lateinit var txtOrder: TextView
    private lateinit var txtAccount: TextView
    private lateinit var homeLayout: LinearLayout
    private lateinit var catLayout: LinearLayout
    private lateinit var orderLayout: LinearLayout
    private lateinit var accountLayout: LinearLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var gpsTracker: GPSTracker? = null
    private val process = Progress()
    private lateinit var dialog: Dialog
    private lateinit var name: TextView
    private lateinit var txtTitle: TextView
    private lateinit var txtDelivery: TextView
    private lateinit var txtCartBadge: TextView
    private lateinit var username: String
    private lateinit var imgCart: ImageView
    private lateinit var imgSearch: ImageView
    private lateinit var options: ActivityOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        options = ActivityOptions.makeCustomAnimation(this, R.anim.up_from_bottom, R.anim.up_from_bottom)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        val navHeader: View = navView.getHeaderView(0)
        name = navHeader.findViewById(R.id.mobile)
        txtTitle = findViewById(R.id.txtTitle)
        txtCartBadge = findViewById(R.id.cart_badge)

          val  menu : Menu = navView.menu;

    // find MenuItem you want to change
    val nav_camara: MenuItem = menu.findItem(R.id.nav_logout);


        username = ClsGeneral.getPreferences(this@HomeActivity, "username").toString()
        val skip = ClsGeneral.getPreferences(this@HomeActivity, "skip").toString()

        if (username==null || username.isNotEmpty())
        name.text = "+91-$username"
        else name.visibility = View.GONE

        if (skip==null || skip.length>0){
            nav_camara.title = "Login"

        }

        txtDelivery = navHeader.findViewById(R.id.delivery)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) { // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

            } else { // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2
                )
            }
        }

        getLatlang()

        initView()

    }



    private fun getLatlang() {
        gpsTracker = GPSTracker(this)
        // check if GPS enabled
        // check if GPS enabled
        if (gpsTracker!!.canGetLocation()) {
            val latitude = gpsTracker!!.latitude
            val longitude = gpsTracker!!.longitude
            try {
                currentAddress = getAdd(LatLng(latitude, longitude))
                ClsGeneral.setPreferences(
                    this@HomeActivity, "currentAddress",
                    currentAddress
                )

                txtDelivery.text = currentAddress
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }

    private fun getAdd(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && addresses.isNotEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(
                        i
                    )
                }
                var sector = address.subLocality
                var locality = address.locality
                addressText = address.getAddressLine(0)
                Log.e("MapsActivity", address.getAddressLine(0))

            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        return addressText
    }


    private fun initView() {
        imgCart = findViewById(R.id.imgCart)
        imgSearch = findViewById(R.id.imgSearch)
        imgHome = findViewById(R.id.imgHome)
        imgCat = findViewById(R.id.imgCat)
        imgAccount = findViewById(R.id.imgAccount)
        imgOrder = findViewById(R.id.imgOrder)

        txtCat = findViewById(R.id.txtCat)
        txtHome = findViewById(R.id.txtHome)
        txtAccount = findViewById(R.id.txtAccount)
        txtOrder = findViewById(R.id.txtOrder)

        catLayout = findViewById(R.id.catLayout)
        orderLayout = findViewById(R.id.orderLayout)
        homeLayout = findViewById(R.id.homeLayout)
        accountLayout = findViewById(R.id.accountLayout)

        setUnselectedBottom()
        imgHome.setColorFilter(
            ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark),
            PorterDuff.Mode.SRC_IN
        )
        txtHome.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        setFragment(HomeFragment(), resources.getString(R.string.home))
        setListener()

    }

    private fun setListener() {
        catLayout.setOnClickListener(this)
        homeLayout.setOnClickListener(this)
        orderLayout.setOnClickListener(this)
        accountLayout.setOnClickListener(this)
        imgCart.setOnClickListener(this)
        imgSearch.setOnClickListener(this)
    }

    private fun setUnselectedBottom() {

        imgHome.colorFilter = null
        txtHome.setTextColor(resources.getColor(R.color.colorLightGery))
        imgOrder.colorFilter = null
        txtOrder.setTextColor(resources.getColor(R.color.colorLightGery))
        imgAccount.colorFilter = null
        txtAccount.setTextColor(resources.getColor(R.color.colorLightGery))
        imgCat.colorFilter = null
        txtCat.setTextColor(resources.getColor(R.color.colorLightGery))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                setUnselectedBottom()
                setFragment(HomeFragment(), resources.getString(R.string.home))
                imgHome.setColorFilter(
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark),
                    PorterDuff.Mode.SRC_IN
                )
                txtHome.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
            R.id.nav_book_grocer -> {
                val i = Intent(applicationContext, BookGroceryTruckActivity::class.java)
                startActivity(i, options.toBundle())
            }
            R.id.nav_cart -> {

                if (Utility.isUserLogIn(context = this)){
                    val i = Intent(applicationContext, CartActivity::class.java)
                    startActivity(i, options.toBundle())
                }
                else{
                    Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    i.putExtra("activity","HomeActivity")
                    startActivity(i, options.toBundle())
                }
            }
            R.id.nav_address -> {

                if (Utility.isUserLogIn(context = this)){
                    val i = Intent(this, MyAddressActivity::class.java)
                    startActivity(i, options.toBundle())
                }
                else{
                    Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    i.putExtra("activity","HomeActivity")
                    startActivity(i, options.toBundle())
                }
            }
            R.id.nav_myorder -> {

                if (Utility.isUserLogIn(context = this)){

                    val i = Intent(applicationContext, MyOrderActivity::class.java)
                    startActivity(i, options.toBundle())
                }
                else{
                    Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    i.putExtra("activity","HomeActivity")
                    startActivity(i, options.toBundle())
                }


            }
            R.id.nav_offer -> {
                Toast.makeText(this, "No offers available.", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about -> {
                val i = Intent(applicationContext, AboutActivity::class.java)
                startActivity(i, options.toBundle())
            }

            R.id.nav_customer_support -> {
                val i = Intent(applicationContext, SupportActivity::class.java)
                startActivity(i, options.toBundle())
            }
            R.id.nav_rate -> {
                drawer_layout.closeDrawer(GravityCompat.START)
                showRateDialog(this@HomeActivity)
            }

            R.id.nav_share -> {
                shareApp()
            }
            R.id.nav_logout -> {
                val skip = ClsGeneral.getPreferences(this@HomeActivity, "skip").toString()

                if (skip==null || skip.isNotEmpty()){
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(i, options.toBundle())
                    finish()
                } else{
                    showLogoutDialog(context = this)
                }
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "5Flavours")
            var shareMessage = "\nLet me recommend you this application\n\n"
            shareMessage =
                shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Choose one"))
        } catch (e: java.lang.Exception) { //e.toString();
        }
    }


    private fun showLogoutDialog(context: Context?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle("Logout")
            .setMessage("Do you want to logout?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    if (context != null) {
                        try {
                            ClsGeneral.setPreferences(
                                this@HomeActivity, "username",
                                ""
                            )
                            val i = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(i, options.toBundle())
                            finish()
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            .setNegativeButton("CANCEL", null)
        builder.show()
    }



    private fun showRateDialog(context: Context?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle("Rate Us")
            .setMessage("Please, Rate the app at 5FLAVOURS!")
            .setPositiveButton("RATE",
                DialogInterface.OnClickListener { dialog, which ->
                    if (context != null) {
                        var link = "market://details?id="
                        link = "https://play.google.com/store/apps/details?id="
                        try {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(link + context.packageName)
                                )
                            )
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            .setNegativeButton("CANCEL", null)
        builder.show()
    }


    private fun setFragment(fragment: Fragment, home: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commitAllowingStateLoss()
        setToolbarBottomTitle(home)
    }

    private fun setToolbarBottomTitle(title: String) {
        txtTitle.text = title
    }

    override fun onClick(p0: View?) {
        when {
            p0!!.id == R.id.catLayout -> {
                setUnselectedBottom()
                setFragment(CategoryFragment(), resources.getString(R.string.categories))
                imgCat.setColorFilter(
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark),
                    PorterDuff.Mode.SRC_IN
                )
                txtCat.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
            p0.id == (R.id.homeLayout) -> {
                setUnselectedBottom()
                setFragment(HomeFragment(), resources.getString(R.string.home))
                imgHome.setColorFilter(
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark),
                    PorterDuff.Mode.SRC_IN
                )
                txtHome.setTextColor(resources.getColor(R.color.colorPrimaryDark))

            }
            p0.id == R.id.accountLayout -> {
                setUnselectedBottom()
                if (Utility.isUserLogIn(context = this)){
                    setFragment(AccountFragment(), resources.getString(R.string.account))
                }
                else{
                    Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    i.putExtra("activity","HomeActivity")
                    startActivity(i, options.toBundle())
                }
                imgAccount.setColorFilter(
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark),
                    PorterDuff.Mode.SRC_IN
                )
                txtAccount.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
            p0.id == R.id.imgCart ->{
                if (Utility.isUserLogIn(context = this)){
                    val i = Intent(applicationContext, CartActivity::class.java)
                    startActivity(i, options.toBundle())
                }
                else{
                    Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, LoginActivity::class.java)
                    i.putExtra("activity","HomeActivity")
                    startActivity(i, options.toBundle())
                }
            }
            p0.id == R.id.imgSearch ->{
                val i = Intent(applicationContext, SearchActivity::class.java)
                startActivity(i, options.toBundle())
            }
        }
    }

    private fun getCartDetails(username: String) {
        process.show(this, "Please Wait...")
        val call = APIClient().apiInterface.callCartList(username)
        ResponseListner(this).getResponse(call,this)
    }

    override fun onApiResponse(response: Any?) {
        process.dialog.dismiss()
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))

        if (response != null) {
            if (response is CartDetailsResponse) {
                if (response.data != null && response.data.size > 0) {
                    txtCartBadge.text = response.data.size.toString()
                    ClsGeneral.setPreferences(this@HomeActivity, "cartsize", response.data.size.toString())
                    txtCartBadge.visibility = View.VISIBLE
                } else {
                    txtCartBadge.visibility = View.GONE
                    ClsGeneral.setPreferences(this@HomeActivity, "cartsize", "")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!Utility.isConnectingToInternet(this@HomeActivity)) {
            dialog = Utility.createErrorDialog(this@HomeActivity, getString(R.string.network_error))!!
            dialog.show()
        } else {
            if (Utility.isUserLogIn(context = this))
            getCartDetails(username)
        }

    }

    override fun onApiFailure(message: String?) {
       try{
           process.dialog.dismiss()
           dialog = Utility.createErrorDialog(
               this@HomeActivity,
               getString(R.string.error)
           )!!
           dialog.show()
       } catch (e: Exception){

       }
    }
}
