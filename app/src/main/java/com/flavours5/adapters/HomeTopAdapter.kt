package com.flavours5.adapters

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flavours5.R
import com.flavours5.models.CategoryResponse
import kotlinx.android.synthetic.main.item_home_top.view.*

class HomeTopAdapter(
    val items: MutableList<CategoryResponse.DataBean>,
    val context: Context, val categoryClickInterface: CategoryClickInterface)
    : RecyclerView.Adapter<HomeTopAdapter.ViewHolder>() {

    interface CategoryClickInterface {
        fun clickAction( i: Int)
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.tvAnimalType?.text = items[p1].categoryname

        val curveRadius = 15F

        Glide.with(context)
            .load(items[p1].categoryrimage)
            .into(p0.img)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            p0.img.outlineProvider = object : ViewOutlineProvider() {

                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view!!.width, (view.height+curveRadius).toInt(), curveRadius)
                }
            }

            p0.img.clipToOutline = true

        }

        p0.itemView.setOnClickListener {
            categoryClickInterface.clickAction(p1)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.item_home_top, p0, false)
        )
    }

    // Gets the number of nameList in the list
    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvAnimalType = view.txtName
        val img = view.img
    }
}

