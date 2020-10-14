package com.flavours5.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Outline
import android.os.Build
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.flavours5.R
import com.flavours5.models.BannerResponse
import com.flavours5.models.OfferResponse
import com.flavours5.ui.home.OfferDetailsActivity
import com.flavours5.ui.prodcuts.ProductsActivity
import com.google.gson.Gson

class OfferImageAdapter(private val context: Context, private val imageModelArrayList: MutableList<OfferResponse.DataBean>) : PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false)!!

        val imageView = imageLayout
            .findViewById(R.id.image) as ImageView
        Glide.with(context).load(imageModelArrayList[position].oferimages).into(imageView)

        imageLayout.setOnClickListener {
            val i = Intent(context, OfferDetailsActivity::class.java)
                i.putExtra("subcatid", imageModelArrayList[position].subcatid.toString())
                i.putExtra("catId", imageModelArrayList[position].cateid.toString())
            context.startActivity(i)
        }

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

}