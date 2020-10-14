package com.flavours5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.models.MyAddressResponse

/**
 * Created by Rakhi.
 * Contact Number : +91 9354674477
 */
class CartAddListAdatper(
    private val addInterfaceMethod: AddInterfaceMethod,
    private val context: Context,
    private val addressBeanList: List<MyAddressResponse.DataBean>
) : RecyclerView.Adapter<CartAddListAdatper.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var title: TextView
        var address: TextView
        override fun onClick(view: View) {
            addInterfaceMethod.AddressInterfaceMethod(adapterPosition)
        }

        init {
            title = view.findViewById(R.id.manage_address_title)
            address = view.findViewById(R.id.manage_address_address)
            itemView.setOnClickListener(this)
        }
    }

    interface AddInterfaceMethod {
        fun AddressInterfaceMethod(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_address_list_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.title.text =
            addressBeanList[position].deliveryPersonName + "\n" + addressBeanList[position].deliveryPersonMOB
        holder.address.text =
            (addressBeanList[position].landmark + ", " + addressBeanList[position].locality
                    + " ," + addressBeanList[position].city
                    + ", " + addressBeanList[position].statename + ", " + addressBeanList[position].pincode)
    }

    override fun getItemCount(): Int {
        return addressBeanList.size
    }

}