package com.flavours5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.models.MyOrderResponse

class MyOrdersAdapter(
    private val mContext: Context,
    private val orderModelListt: MutableList<MyOrderResponse.DataBean?>?,
    private val viewDetailsInterface: ViewDetailsInterface
) : RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder>() {

    interface ViewDetailsInterface {
        fun viewDetails(pos: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_order_list_layout_obj, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtPay.text = "Payment type : " + orderModelListt!![position]!!.paymentMethod
        holder.txtStatus.text = orderModelListt[position]!!.deliveryStatus
        holder.txtDate.text = "Delivery Date: " + orderModelListt[position]!!.deleverydate
        holder.txtTime.text = "Delivery Time: " + orderModelListt[position]!!.deleverytime
        holder.txtPrice.text = mContext.getString(R.string.rupees_symbol) + orderModelListt[position]!!.orderTotal
        holder.txtOrderID.text = "Order Id : #" + orderModelListt[position]!!.orderId
        holder.txtOrderDate.text = "Order Date : " + orderModelListt[position]!!.createdate
    }

    override fun getItemCount(): Int {
        return orderModelListt!!.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtOrderID: TextView
        var txtDate: TextView
        var txtTime: TextView
        var txtPrice: TextView
        var txtStatus: TextView
        var txtViewDetails: TextView
        var txtPay: TextView
        var txtOrderDate: TextView

        init {
            txtOrderID = view.findViewById(R.id.txtOrderId)
            txtDate = view.findViewById(R.id.txtDate)
            txtOrderDate = view.findViewById(R.id.txtOrderDate)
            txtPay = view.findViewById(R.id.txtPayment)
            txtPrice = view.findViewById(R.id.txtPrice)
            txtTime = view.findViewById(R.id.txtTime)
            txtStatus = view.findViewById(R.id.txtStatus)
            txtViewDetails = view.findViewById(R.id.txtViewDetails)
            txtViewDetails.setOnClickListener {
                viewDetailsInterface.viewDetails(
                    adapterPosition
                )
            }
        }
    }

}