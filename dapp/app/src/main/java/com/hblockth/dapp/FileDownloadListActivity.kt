package com.hblockth.dapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hblockth.dapp.adapter.AddressViewAdapter
import com.hblockth.dapp.adapter.DownloadListViewAdapter
import com.hblockth.dapp.databinding.ActivityDownloadListBinding
import com.hblockth.dapp.viewmodels.*
import java.text.SimpleDateFormat
import java.util.*

class FileDownloadListActivity: AppCompatActivity() {

    private lateinit var mDownloadListViewModel: DownloadListViewModel
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    private lateinit var mBinding: ActivityDownloadListBinding
    private lateinit var mAdapter: DownloadListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        getTxIdList()
        super.onCreate(savedInstanceState)
    }

    fun getTxIdList() {
        mDefaultAddressViewModel = ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
        mDefaultAddressViewModel.addressModel.observe(this, Observer { defaultaddressInfo ->
            if(defaultaddressInfo != null)
            {
                mDownloadListViewModel =  ViewModelProviders.of(this, DownloadListViewModelFactory(this.application, defaultaddressInfo.address))
                    .get<DownloadListViewModel>(
                        DownloadListViewModel::class.java
                    )
                mDownloadListViewModel.uploadTxIdModel.observe(this, Observer { uploadTxIdList ->
                    mAdapter = DownloadListViewAdapter(emptyList())
                    //テストデータの生成
                    //https://qiita.com/fu_neko/items/722e0ab5f0f1255f87bf
                    val date = SimpleDateFormat("yyyy/MM/dd").format(Date())


                    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_download_list)
                    mBinding.viewModel = mDownloadListViewModel
                    mBinding.txidRecyclerView.apply {
                        adapter = mAdapter
                        layoutManager = LinearLayoutManager(this@FileDownloadListActivity)
                    }
                    mAdapter.txIdList = uploadTxIdList
                    mAdapter.notifyDataSetChanged()
                    // インターフェースの実装
                    mAdapter.setOnItemClickListener(object: DownloadListViewAdapter.OnItemClickListener{
                        override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                            //changeAddress(clickedText)
                            println("aaaaaaaa")
                        }
                    })
                })
            }
        })
    }
}