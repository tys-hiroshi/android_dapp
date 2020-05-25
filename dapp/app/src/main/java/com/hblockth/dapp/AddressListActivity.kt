package com.hblockth.dapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class AddressListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        //テストデータの生成
        val date = SimpleDateFormat("yyyy/MM/dd").format(Date())
        val memoList = mutableListOf<Memo>()
        repeat((0..100).count()) { memoList.add(Memo("僕の名前は麻婆", date)) }

        //RecyclerViewにAdapterとLayoutManagerを設定
        findViewById<RecyclerView>(R.id.RecyclerViewAddressList).also { recyclerView: RecyclerView ->
            recyclerView.adapter = MemoViewAdapter(this, memoList)
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }
}
