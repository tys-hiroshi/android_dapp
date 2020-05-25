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
//        val userIconImageView: ImageView = view.findViewById(R.id.userIcon)
//        val userNameTextView: TextView = view.findViewById(R.id.userName)
//        val dateTextView: TextView = view.findViewById(R.id.date)
//        val contentTextView: TextView = view.findViewById(R.id.add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
        AddressViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_address_list, parent, false))

    override fun getItemCount(): Int = memoList.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.userIconImageView.setImageResource(R.mipmap.ic_launcher)
        holder.userNameTextView.text = "fu_neko"
        holder.dateTextView.text = memoList[position].date
        holder.contentTextView.text = memoList[position].content
    }
}