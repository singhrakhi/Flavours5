package com.flavours5.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flavours5.R
import com.flavours5.models.MyAddressResponse
import com.flavours5.models.OfferResponse
import com.flavours5.ui.home.OfferDetailsActivity

class MyOfferAdapter(
    private val imageModelArrayList: MutableList<OfferResponse.DataBean>,
    private val context: Context)
    : RecyclerView.Adapter<MyOfferAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.slidingimages_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        Glide.with(context).load(imageModelArrayList[position].oferimages).into(holder.imgslider)

        holder.imgslider.setOnClickListener {
            val i = Intent(context, OfferDetailsActivity::class.java)
            i.putExtra("subcatid", imageModelArrayList[position].subcatid.toString())
            i.putExtra("catId", imageModelArrayList[position].cateid.toString())
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var imgslider: ImageView


        init {
            imgslider = view.findViewById(R.id.image)
        }
    }

}