package com.hblockth.dapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hblockth.dapp.adapter.AddressViewAdapter
import com.hblockth.dapp.model.AddressModel
import java.text.SimpleDateFormat
import java.util.*

class AddressListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        //テストデータの生成
        //https://qiita.com/fu_neko/items/722e0ab5f0f1255f87bf
        val date = SimpleDateFormat("yyyy/MM/dd").format(Date())
        val addressList = mutableListOf<AddressModel>()
        repeat((0..100).count()) { addressList.add(AddressModel("address", date)) }

        //RecyclerViewにAdapterとLayoutManagerを設定
        findViewById<RecyclerView>(R.id.addressRecyclerView).also { recyclerView: RecyclerView ->
            recyclerView.adapter = AddressViewAdapter(this, addressList)
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }
}
