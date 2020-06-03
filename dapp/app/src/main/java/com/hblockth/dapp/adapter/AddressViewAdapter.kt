package com.hblockth.dapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hblockth.dapp.R
import com.hblockth.dapp.room.models.addressmng.AddressModel
import androidx.databinding.DataBindingUtil
import com.hblockth.dapp.utils.Utils

class AddressViewAdapter(var addressList: List<AddressModel>) :
    RecyclerView.Adapter<AddressViewAdapter.AddressViewHolder>() {

    // リスナー格納変数
    lateinit var listener: OnItemClickListener

    class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date)
        val addressTextView: TextView = view.findViewById(R.id.address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
        AddressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_address, parent, false))


    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        //holder.dateTextView.text = addressList[position].id
        holder.addressTextView.text = addressList[position].address
        holder.dateTextView.text = Utils.dateTimeFormatter.format(addressList[position].createdAt)
        // タップしたとき
        holder.addressTextView.setOnClickListener {
            listener.onItemClickListener(it, position, addressList[position].address)
        }
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: String)
    }

    //https://qiita.com/YS-BETA/items/f54bed772d502c5c06f0
    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}