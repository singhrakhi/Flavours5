package com.flavours5.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flavours5.R
import com.flavours5.adapters.MyCartAdapter.MyViewHolder
import com.flavours5.models.CartDetailsResponse

class MyCartAdapter(
    var mContext: Context,
    private val cartProductModelArrayList: List<CartDetailsResponse.DataBean>,
    private val cartInterface: CartInterface) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return MyViewHolder(itemView)
    }

    interface CartInterface {
        fun cartInterfaceMethod(position: Int, click_type: String?, btnUpdate: TextView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_product_name.text = cartProductModelArrayList[position].productName
        holder.txt_unit.text = cartProductModelArrayList[position].unit
        holder.layoutAddItem.visibility = View.VISIBLE
        holder.addToCart.visibility = View.GONE
        holder.txt_original_price.paintFlags =
            holder.txt_original_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.txt_original_price.text =
            mContext.getString(R.string.rupees_symbol) + cartProductModelArrayList[position].price
        if (cartProductModelArrayList[position].discount.toString() == "" || cartProductModelArrayList[position].discount == 0
        ) {
            holder.text_percentage_off.visibility = View.GONE
            holder.txt_original_price.visibility = View.GONE
        } else {
            holder.text_percentage_off.text =
                cartProductModelArrayList[position].discount.toString() + "% OFF"
        }
        holder.txt_paid_price.text =
            mContext.getString(R.string.rupees_symbol) + cartProductModelArrayList[position].discountedPrice
        holder.txt_item_quantity.text = cartProductModelArrayList[position].quantity.toString()
        //Product Click Listener
        Glide.with(mContext)
            .load(cartProductModelArrayList[position].productImages)
            .into(holder.img_product_image)
    }


    override fun getItemCount(): Int {
        return cartProductModelArrayList.size
    }

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var img_product_image: ImageView
        var txt_product_name: TextView
        var txt_unit: TextView
        var card_view_product: CardView
        var text_percentage_off: TextView
        var txt_original_price: TextView
        var txt_paid_price: TextView
        var img_btn_minus: TextView
        var img_btn_plus: TextView
        var txt_item_quantity: TextView
        var addToCart: TextView
        var layoutAddItem: LinearLayout

        init {
            card_view_product = view.findViewById(R.id.card_view_product)
            text_percentage_off = view.findViewById(R.id.text_percentage_off)
            //            text_cash_back = view.findViewById(R.id.text_cash_back);
            img_product_image = view.findViewById(R.id.img_product_image)
            //            img_remove_product =  view.findViewById(R.id.img_remove_product);
//            img_remove_product.setVisibility(View.GONE);
            txt_product_name = view.findViewById(R.id.txtProductName)
            txt_unit = view.findViewById(R.id.txtQuantity)
            //            txt_sub_name =  view.findViewById(R.id.txt_sub_name);
            txt_original_price = view.findViewById(R.id.txtOldPrice)
            txt_paid_price = view.findViewById(R.id.txtPayablePrice)
            //            txt_add_to_cart = view.findViewById(R.id.txt_add_to_cart);
            img_btn_plus = view.findViewById(R.id.tv_plus)
            img_btn_minus = view.findViewById(R.id.tv_minus)
            txt_item_quantity = view.findViewById(R.id.tv_item_count)
            addToCart = view.findViewById(R.id.tv_add_to_cart)
            layoutAddItem = view.findViewById(R.id.layoutUpdate)

            img_btn_plus.setOnClickListener {
                cartInterface.cartInterfaceMethod(adapterPosition, "plus",img_btn_plus)
            }

            img_btn_minus.setOnClickListener {
                cartInterface.cartInterfaceMethod(adapterPosition, "minus",img_btn_minus)
            }

        }
    }

}