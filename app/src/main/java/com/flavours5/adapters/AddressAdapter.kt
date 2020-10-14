package com.flavours5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flavours5.R
import com.flavours5.models.MyAddressResponse

class AddressAdapter(
    private val applicationContext: Context,
    private val savedAddressList: List<MyAddressResponse.DataBean>,
    private val addressActionInterface: AddressActionInterface
) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    interface AddressActionInterface {
        fun clickAction(tag: String?, i: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_address_list_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.txtName.text =
            savedAddressList[position].deliveryPersonName + "\n" + savedAddressList[position].deliveryPersonMOB
        holder.txtDetails.text =
            (savedAddressList[position].landmark + ", " + savedAddressList[position].locality
                    + " ," + savedAddressList[position].city
                    + ", " + savedAddressList[position].statename + ", " + savedAddressList[position].pincode)
    }

    override fun getItemCount(): Int {
        return savedAddressList.size
    }

    inner class MyViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var txtName: TextView
        var txtDetails: TextView
        var txtDelete: TextView
        var txtEdit: TextView

        init {
            txtName = view.findViewById(R.id.manage_address_title)
            txtDetails = view.findViewById(R.id.manage_address_address)
            txtDelete = view.findViewById(R.id.manage_address_txtDelete)
            txtEdit = view.findViewById(R.id.manage_address_txtEdit)
            txtEdit.setOnClickListener {
                addressActionInterface.clickAction(
                    "edit",
                    adapterPosition
                )
            }
            txtDelete.setOnClickListener {
                addressActionInterface.clickAction(
                    "delete",
                    adapterPosition
                )
            }
        }
    }

}