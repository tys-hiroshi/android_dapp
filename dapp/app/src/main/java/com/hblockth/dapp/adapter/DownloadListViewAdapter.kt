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
import com.hblockth.dapp.room.models.addressmng.UploadTxIdModel
import com.hblockth.dapp.utils.Utils

class DownloadListViewAdapter(var txIdList: List<UploadTxIdModel>) :
    RecyclerView.Adapter<DownloadListViewAdapter.TxIdViewHolder>() {

    // リスナー格納変数
    lateinit var listener: OnItemClickListener

    class TxIdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date)
        val txIdTextView: TextView = view.findViewById(R.id.txid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TxIdViewHolder =
        TxIdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_address, parent, false))


    override fun getItemCount(): Int = txIdList.size

    override fun onBindViewHolder(holder: TxIdViewHolder, position: Int) {
        //holder.dateTextView.text = addressList[position].id
        holder.txIdTextView.text = txIdList[position].txid
        holder.dateTextView.text = Utils.dateTimeFormatter.format(txIdList[position].createdAt)
        // タップしたとき
        holder.txIdTextView.setOnClickListener {
            listener.onItemClickListener(it, position, txIdList[position].txid)
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