package com.flavours5.ui.home

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.flavours5.R
import com.flavours5.adapters.*
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.BannerResponse
import com.flavours5.models.CategoryResponse
import com.flavours5.models.OfferResponse
import com.flavours5.ui.category.CategoryDetailsActivity
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import com.viewpagerindicator.CirclePageIndicator
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), OnResponseInterface, HomeTopAdapter.CategoryClickInterface {
    private val process = Progress()
    private lateinit var dialog: Dialog

    private var mPager: ViewPager? = null
    private lateinit var offerPager: ViewPager
    private var currentPage = 0
    private var NUM_PAGES = 0

    private lateinit var topRecycler: RecyclerView
    private lateinit var indicator: CirclePageIndicator
    private lateinit var groceryLayout: TextView
    private lateinit var options: ActivityOptions
    private lateinit var offerindicator: CirclePageIndicator
    private lateinit var latestRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        intiView(root)
        return root
    }

    private fun intiView(root: View?) {
        options =
            ActivityOptions.makeCustomAnimation(
                context,
                R.anim.up_from_bottom,
                R.anim.up_from_bottom
            )
        topRecycler = root!!.findViewById(R.id.topRecycler)
        groceryLayout = root.findViewById(R.id.groceryLayout)
        topRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val context: Context = requireContext()
        mPager = root.findViewById(R.id.pager)
        indicator = root.findViewById(R.id.indicator)
        offerPager = root.findViewById(R.id.offerPager)
        offerindicator = root.findViewById(R.id.offerIndicator)

        if (!Utility.isConnectingToInternet(context)) {
            dialog = Utility.createErrorDialog(
                context,
                getString(R.string.network_error)
            )!!
            dialog.show()
        } else {
            getCategoryData()

            getSliderData()

            getOffer()
        }
        latestRecycler = root.findViewById(R.id.latestRecycler)
        latestRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        groceryLayout.setOnClickListener {
            val i = Intent(context, BookGroceryTruckActivity::class.java)
            startActivity(i, options.toBundle())
        }

    }

    private fun setViewPager(bannerList: MutableList<BannerResponse.DataBean>) {
        mPager!!.adapter =
            context?.let {
                SlidingImageAdapter(it, bannerList)
            }
        indicator.setViewPager(mPager)
        val density = resources.displayMetrics.density
        indicator.radius = 5 * density
        NUM_PAGES = bannerList.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })
    }

    private fun setOfferViewPager(bannerList: MutableList<OfferResponse.DataBean>) {
        offerPager!!.adapter =
            context?.let {
                OfferImageAdapter(it, bannerList)
            }
        offerindicator.setViewPager(offerPager)
        val density = resources.displayMetrics.density
        offerindicator.radius = 5 * density
        NUM_PAGES = bannerList.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            offerPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator

        offerindicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position


            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })

    }

    private fun setFeaturesAdapter() {
        latestRecycler.adapter = MyOfferAdapter(offerList, requireContext())
    }

    private fun getCategoryData() {
        process.show(requireContext(), "Please Wait...")
        val call = APIClient().apiInterface.callCategoryData()
        ResponseListner(this).getResponse(call,context)
    }

    private fun getSliderData() {
        val call = APIClient().apiInterface.callSliderData()
        ResponseListner(this).getResponse(call,context)
    }

    private fun getOffer() {
        val call = APIClient().apiInterface.callOfferData()
        ResponseListner(this).getResponse(call,context)
    }

    private var categoryList: MutableList<CategoryResponse.DataBean> = ArrayList()
    private var offerList: MutableList<OfferResponse.DataBean> = ArrayList()

    override fun onApiResponse(response: Any?) {
        process.dialog.dismiss()
        Log.d("TAG", "onApiResponse: " + Gson().toJson(response))

        if (response != null) {
            if (response is CategoryResponse) {
                if (response.data != null) {
                    if (categoryList != null)
                        categoryList.clear()

                    categoryList = response.data
                    setAdapter(categoryList)
                }
            } else if (response is BannerResponse) {
                if (response.data != null) {
                    val bannerList = response.data
//        set viewpager
                    setViewPager(bannerList)
                }
            } else if (response is OfferResponse){
                if (response.data!=null && response.data.size>0){
                    offerList = response.data
                    setOfferViewPager(offerList)

                    setFeaturesAdapter()
                }
            }
        }
    }

    private fun setAdapter(categoryList: MutableList<CategoryResponse.DataBean>) {
        topRecycler.adapter = context?.let {
            HomeTopAdapter(categoryList, it, this)
        }
    }

    override fun onApiFailure(message: String?) {
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(
            context,
            getString(R.string.error)
        )!!
        dialog.show()
    }

    override fun clickAction(j: Int) {
        val i = Intent(context, CategoryDetailsActivity::class.java)
        i.putExtra("categoryList", Gson().toJson(categoryList))
        i.putExtra("catName", categoryList[j].categoryname)
        i.putExtra("catId", categoryList[j].id.toString())
        startActivity(i, options.toBundle())
    }

}
