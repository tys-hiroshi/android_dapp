package com.hblockth.dapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.hblockth.dapp.adapter.AddressViewAdapter
import com.hblockth.dapp.model.AddressModel
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.db.AddressManageDatabase
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

//        val db = Room.databaseBuilder(
//            applicationContext,
//            AddressManageDatabase::class.java, "database-dapp"
//        ).build()
        val addressManageDao: AddressManageDao = DApplication.db.addressManageDao()
        val addresses:List<com.hblockth.dapp.room.models.addressmng.AddressModel> = addressManageDao.getAll()

        for (item in addresses){
            addressList.add(AddressModel(item.address, "2020/05/10"))
        }

        //RecyclerViewにAdapterとLayoutManagerを設定

        findViewById<RecyclerView>(R.id.addressRecyclerView).also { recyclerView: RecyclerView ->
            recyclerView.adapter = AddressViewAdapter(this, addressList)
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }
}
