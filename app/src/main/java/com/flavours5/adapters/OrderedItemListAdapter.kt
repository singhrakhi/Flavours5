package com.flavours5.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flavours5.R
import com.flavours5.models.OrderDetailsResponse

class OrderedItemListAdapter(
    var context: Context,
    var oredredArrayList: MutableList<OrderDetailsResponse.DataBean>
) : RecyclerView.Adapter<OrderedItemListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ordered_details, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) { //        try {
         var productModel = oredredArrayList.get(position);
            if (productModel.productImage == "null") {
                holder.txt_product_name.text = "Not Available";
            } else
                holder.txt_product_name.text = productModel.productName;


        holder.txtPrice.paintFlags = holder.txtPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG;
        holder.txtPrice.text = "Price : " + context.getString(R.string.rupees_symbol) + " " + productModel.price;

        holder.txt_paid_price.text = "Amount : " + context.getString(R.string.rupees_symbol) + " " + (productModel.discountedPrice * productModel.quantity)
        holder.txtQuantity.text = "Quantity : " + productModel.quantity;
        holder.txt_unit.text = "| Unit : " + productModel.unit;
            if (productModel.productImage == "null") {
                Glide.with(context).load(productModel.productImage).into(holder.img_product_image);
            } else
                Glide.with(context).load(productModel.productImage).into(holder.img_product_image);

    }

    override fun getItemCount(): Int {
        return oredredArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img_product_image: ImageView
        var img_remove_product: ImageView
        var txtPrice: TextView
        var txtQuantity: TextView
        var txtDiscount: TextView
        var txt_product_name: TextView
        var txt_unit: TextView
        var txt_sub_name: TextView
        var card_view_product: CardView
        var text_percentage_off: TextView
        var text_cash_back: TextView
        var txt_original_price: TextView? = null
        var txt_paid_price: TextView
        var txt_add_to_cart: TextView

        init {
            card_view_product = view.findViewById(R.id.card_view_product)
            text_percentage_off = view.findViewById(R.id.text_percentage_off)
            text_cash_back = view.findViewById(R.id.text_cash_back)
            img_product_image = view.findViewById(R.id.img_product_image)
            img_remove_product =
                view.findViewById(R.id.img_remove_product)
            img_remove_product.visibility = View.GONE
            txt_product_name = view.findViewById(R.id.txt_product_name)
            txt_unit = view.findViewById(R.id.txt_unit)
            txt_sub_name = view.findViewById(R.id.txt_sub_name)
            //txt_original_price = view.findViewById(R.id.txt_original_price);
            txt_paid_price = view.findViewById(R.id.txt_paid_price)
            txt_add_to_cart = view.findViewById(R.id.txt_add_to_cart)
            txtPrice = view.findViewById(R.id.txt_price)
            txtDiscount = view.findViewById(R.id.txt_discount)
            txtQuantity = view.findViewById(R.id.txt_quantity)
            txt_unit = view.findViewById(R.id.txtUnit)
        }
    }

}