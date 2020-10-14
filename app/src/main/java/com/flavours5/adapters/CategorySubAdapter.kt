package com.flavours5.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.models.SubCategoryResponse
import com.flavours5.ui.prodcuts.ProductsActivity
import kotlinx.android.synthetic.main.item_list.view.*


class CategorySubAdapter(
    val items: MutableList<SubCategoryResponse.DataBean>?,
    val context: Context,
    val categoryname: String)
    : RecyclerView.Adapter<ViewHolder3>() {

    override fun onBindViewHolder(p0: ViewHolder3, p1: Int) {
        p0.txtNameTitle.text = items?.get(p1)?.subcategoryname
        p0.txtNameTitle.setOnClickListener {
            val intent = Intent(context, ProductsActivity::class.java)
            intent.putExtra("categoryName", categoryname)
            intent.putExtra("subCatName", items!![p1].subcategoryname)
            intent.putExtra("subCatId", items[p1].id)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder3 {
        return ViewHolder3(
            LayoutInflater.from(context).inflate(R.layout.item_list, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

}

class ViewHolder3 (view: View) : RecyclerView.ViewHolder(view) {
    val txtNameTitle: TextView = view.expandedListItem

}