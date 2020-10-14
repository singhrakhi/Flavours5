package com.flavours5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.models.CategoryResponse
import com.flavours5.models.SubCategoryResponse
import kotlinx.android.synthetic.main.item_group.view.*

class CategoryAdapter(
    val items: MutableList<CategoryResponse.DataBean>,
    val context: Context,
    val subCatListAll: HashMap<Int, MutableList<SubCategoryResponse.DataBean>>
)
    : RecyclerView.Adapter<ViewHolder2>() {

    private lateinit var categorySubAdapter: CategorySubAdapter

    override fun onBindViewHolder(p0: ViewHolder2, p1: Int) {
        p0.txtNameTitle?.text = items[p1].categoryname
        p0.recyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        if (subCatListAll!=null && subCatListAll.size>0){
            categorySubAdapter = CategorySubAdapter(subCatListAll[items[p1].id],context,items[p1].categoryname)
            p0.recyclerView.adapter = categorySubAdapter
        }

        p0.txtNameTitle.setOnClickListener {
            if (p0.recyclerView.visibility == View.VISIBLE)
            p0.recyclerView.visibility = View.GONE
            else  p0.recyclerView.visibility = View.VISIBLE
        }

    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder2 {
        return ViewHolder2(
            LayoutInflater.from(
                context
            ).inflate(R.layout.item_group, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }



}

class ViewHolder2 (view: View) : RecyclerView.ViewHolder(view) {
    val txtNameTitle: TextView = view.listTitle
    val recyclerView = view.subCatRecycler
}