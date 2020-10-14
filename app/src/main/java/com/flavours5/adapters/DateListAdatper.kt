package com.flavours5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import java.util.*

/**
 * Created by Rakhi.
 * Contact Number : +91 9958187463
 */
class DateListAdatper(
    private val addInterfaceMethod: SelectDateInterface,
    private val context: Context,
    private val dateSlot: ArrayList<String>,
    private val type: String
) : RecyclerView.Adapter<DateListAdatper.MyViewHolder>() {

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var title: TextView

        init {
            title = view.findViewById(R.id.txtDate)
            itemView.setOnClickListener {
                addInterfaceMethod.selectDate(
                    adapterPosition, type
                )
            }
        }
    }

    interface SelectDateInterface {
        fun selectDate(position: Int, type: String?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_date, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.title.text = dateSlot[position]
    }

    override fun getItemCount(): Int {
        return dateSlot.size
    }

}