package com.flavours5.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Outline
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flavours5.R
import com.flavours5.common.ClsGeneral
import com.flavours5.common.Utility
import com.flavours5.models.AddToCartResponse
import com.flavours5.models.OtpVerifyResponse
import com.flavours5.models.ProductResponse
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(
    val items: MutableList<ProductResponse.DataBean>,
    val context: Context, val clickData: ClickData,val addToCartInterface: AddToCartInterface,
    val productDetailInterface: ProductDetailInterface
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>(), OnResponseInterface {

    private lateinit var dialog: Dialog

    interface AddToCartInterface{
        fun clickAdd(i: Int,click_type: String, layout: LinearLayout,txtAdd: TextView)
    }

    interface ProductDetailInterface{
        fun clickItem(i: Int,click_type: String)
    }

    interface ClickData {
        fun getDetails(position: Int, click_type: String, tvCount: TextView, btnAdd: TextView, updateQYT:LinearLayout)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtProductName?.text = items[p1].productName
        p0.txtPayablePrice?.text =
            context.resources.getString(R.string.rupees_symbol) + items[p1].discountPrice.toString()
        p0.txtOldPrice?.text =
            context.resources.getString(R.string.rupees_symbol) + items[p1].price.toString()
        p0.txtOldPrice.paintFlags = p0.txtOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        if (items[p1].discount > 0)
            p0.text_percentage_off?.text = items[p1].discount.toString() + ("% OFF")

        p0.txtQuantity?.text = items[p1].unit

        val curveRadius = 15F

        Glide.with(context)
            .load(items[p1].productImage)
            .into(p0.img)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            p0.img.outlineProvider = object : ViewOutlineProvider() {

                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(
                        0,
                        0,
                        view!!.width,
                        (view.height + curveRadius).toInt(),
                        curveRadius
                    )
                }
            }

            p0.img.clipToOutline = true

        }

        p0.txtAdd.setOnClickListener {
//            p0.updateQYT.visibility = View.VISIBLE
//            p0.txtAdd.visibility = View.GONE
            addToCartInterface.clickAdd(p1,"add",p0.updateQYT,p0.txtAdd)
        }

        p0.tvMinus.setOnClickListener {
            clickData.getDetails(p1, "minus", p0.txtItemCount, p0.txtAdd, p0.updateQYT)
        }
        p0.tvPlus.setOnClickListener {
            clickData.getDetails(p1, "plus", p0.txtItemCount,p0.txtAdd, p0.updateQYT)

        }

        p0.itemView.setOnClickListener {
            productDetailInterface.clickItem(p1,"itemsDetails")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.item_product, p0, false)
        )
    }

    // Gets the number of nameList in the list
    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val txtProductName = view.txtProductName
        val txtPayablePrice = view.txtPayablePrice
        val txtOldPrice = view.txtOldPrice
        val text_percentage_off = view.text_percentage_off
        val txtQuantity = view.txtQuantity
        val img = view.img_product_image
        val txtAdd = view.tv_add_to_cart
        val updateQYT = view.layoutUpdate
        val txtItemCount = view.tv_item_count
        val tvMinus = view.tv_minus
        val tvPlus = view.tv_plus

    }

    override fun onApiResponse(response: Any?) {

        if (response!=null){
            Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
            if (response is AddToCartResponse){
                if (response.status!=null && response.status=="Success"){
                    dialog = Utility.createErrorDialog(
                        context,
                        response.message
                    )!!
                    dialog.show()
                } else{
                    dialog = Utility.createErrorDialog(
                        context,
                        response.message
                    )!!
                    dialog.show()
                }
            }
        }
    }

    override fun onApiFailure(message: String?) {
        dialog = Utility.createErrorDialog(
            context,
            context.getString(R.string.error)
        )!!
        dialog.show()
    }
}

