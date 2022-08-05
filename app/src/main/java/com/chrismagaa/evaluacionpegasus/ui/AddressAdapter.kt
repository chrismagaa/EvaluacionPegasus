package com.chrismagaa.evaluacionpegasus.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chrismagaa.evaluacionpegasus.R
import com.chrismagaa.evaluacionpegasus.data.local.Address
import com.chrismagaa.evaluacionpegasus.databinding.ItemAddressBinding

class AddressAdapter(
    private val onDeleteClick: (Address) -> Unit
): RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var items: List<Address> = ArrayList()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemAddressBinding.bind(itemView)

        fun bind(item: Address, onDeleteClick: (Address) -> Unit){
            binding.tvDistance.text = "${item.distance} - ${item.duration}"
            binding.tvAddress.text = "${item.lat}, ${item.lng}"
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onDeleteClick)
    }

    override fun getItemCount(): Int  = items.size

    fun setData(listAddress: List<Address>){
        items = listAddress
        notifyDataSetChanged()
    }

}