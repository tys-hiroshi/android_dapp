package com.hblockth.dapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hblockth.dapp.R
import com.hblockth.dapp.model.AddressModel

class AddressViewAdapter(private val context: Context, private val addressList: List<AddressModel>) :
    RecyclerView.Adapter<AddressViewAdapter.AddressViewHolder>() {

    class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date)
        val addressTextView: TextView = view.findViewById(R.id.address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
        AddressViewHolder(LayoutInflater.from(context).inflate(R.layout.list_address, parent, false))

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.dateTextView.text = addressList[position].date
        holder.addressTextView.text = addressList[position].address
    }
}