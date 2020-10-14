package com.flavours5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flavours5.R
import kotlinx.android.synthetic.main.item_feat_top.view.*
import kotlinx.android.synthetic.main.item_home_top.view.*

class HomeLatestAdapter(val items : ArrayList<String>,val image : ArrayList<String>, val context: Context)
    : RecyclerView.Adapter<ViewHolder1>() {

    override fun onBindViewHolder(p0: ViewHolder1, p1: Int) {
        p0.tvAnimalType?.text = items[p1]

        val curveRadius = 15F

        Glide.with(context)
            .load(image[p1])
            .into(p0.img)
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            p0.img.outlineProvider = object : ViewOutlineProvider() {

                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun getOutline(view: View?, outline: Outline?) {
                    outline?.setRoundRect(0, 0, view!!.width, (view.height+curveRadius).toInt(), curveRadius)
                }
            }

            p0.img.clipToOutline = true

        }
*/

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder1 {
        return ViewHolder1(
            LayoutInflater.from(
                context
            ).inflate(R.layout.item_feat_top, p0, false)
        )
    }

    // Gets the number of nameList in the list
    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder1 (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvAnimalType: TextView = view.txtame
    val img = view.image
}