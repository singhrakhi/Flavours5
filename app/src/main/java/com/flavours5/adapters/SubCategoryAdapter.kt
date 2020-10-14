package com.flavours5.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.models.SubCategoryResponse
import java.util.*


class SubCategoryAdapter(
    private val context: Context,
    val subCatList: MutableList<SubCategoryResponse.DataBean>,
    val click: CateClickInterface
) : RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtName: TextView

        init {
            txtName = view.findViewById<View>(R.id.txtCuisionName) as TextView

        }
    }

    var row_index = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cuision_list_category, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.txtName.text = subCatList[position].subcategoryname

        if (row_index == position) {
            holder.txtName.background =
                context.resources.getDrawable(R.drawable.shape_background_theme)
            holder.txtName.setTextColor(context.resources.getColor(R.color.colorWhite))
        } else {
            holder.txtName.setTextColor(context.resources.getColor(R.color.colorBlack))
            holder.txtName.background = context.resources.getDrawable(R.drawable.shape_theme_border)
        }

       holder.txtName.setOnClickListener {
            row_index = position;
            notifyDataSetChanged();
            click.subCatClick(position)
        }
    }

    override fun getItemCount(): Int {
        return subCatList.size
    }

    interface CateClickInterface {
        fun subCatClick(position: Int)
    }

}