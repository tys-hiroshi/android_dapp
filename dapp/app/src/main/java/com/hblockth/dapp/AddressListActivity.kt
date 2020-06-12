package com.hblockth.dapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hblockth.dapp.adapter.AddressViewAdapter
import com.hblockth.dapp.databinding.ActivityAddressListBinding
import com.hblockth.dapp.utils.Utils
import com.hblockth.dapp.viewmodels.AddressListViewModel
import com.hblockth.dapp.viewmodels.DefaultAddressViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


//import com.hblockth.dapp.databinding.ActivityAddressListBinding

class AddressListActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityAddressListBinding
    private lateinit var mAddressListViewModel: AddressListViewModel
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    private lateinit var mAdapter: AddressViewAdapter
    private var hasScrolledSteep = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)

        mAddressListViewModel = ViewModelProviders.of(this).get(AddressListViewModel::class.java)
        mDefaultAddressViewModel =  ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
        mAdapter = AddressViewAdapter(emptyList())
        //https://qiita.com/fu_neko/items/722e0ab5f0f1255f87bf
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_address_list)
        mBinding.viewModel = mAddressListViewModel
        mBinding.addressRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@AddressListActivity)
        }
        mAddressListViewModel.addresses.observe(this, Observer { addressList ->
            mAdapter.addressList = addressList
            mAdapter.notifyDataSetChanged()
            hasScrolledSteep = true
        })
        //RecyclerViewにAdapterとLayoutManagerを設定

//        findViewById<RecyclerView>(R.id.addressRecyclerView).also { recyclerView: RecyclerView ->
//            recyclerView.adapter = AddressViewAdapter(this, )
//            recyclerView.layoutManager = LinearLayoutManager(this)
//        }

//        // インターフェースの実装
//        mAdapter.setOnItemClickListener(object:AddressViewAdapter.OnItemClickListener{
//            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
//
//                changeAddress(clickedText)
////                when(view.id){
////                    R.id.TextViewAddressInAddressList -> {
////                        changeAddress(clickedText)
////                    }
////                    R.id.buttonRemoveAddressInfo -> {
////                        removeAddressInfo(clickedText)
////                    }
////                }
//            }
//        })

        mAdapter.setOnItemClickListener(object:AddressViewAdapter.OnItemClickListener{
            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                when(view.id){
                    R.id.TextViewAddressInAddressList -> {
                        changeAddress(clickedText)
                    }
                    R.id.buttonRemoveAddressInfo -> {
                        removeAddressInfo(clickedText)
                    }
                }
            }
        })
    }

    /* Sendボタン押下時 */
    fun changeAddress(address: String) {
        val intent: Intent = Intent(this@AddressListActivity,
            MainActivity::class.java)
        //val generateAddress : GenerateAddress? = main(args)
        //println("generateAddress:${generateAddress?.address}")
        //val result = getText("https://bsvnodeapi.herokuapp.com/generateaddress/test")
        //mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy
        //println(result)
        //TODO: Default Address を更新する
        mDefaultAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
            if(addressInfo != null)
            {
                Completable.fromAction { mDefaultAddressViewModel.defaultAddressDelete(address as String) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.single())
                    .subscribe()

            }
        })
        intent.putExtra(Utils.SELECTED_ADDRESS, address)
        startActivity(intent)
    }


    /* Sendボタン押下時 */
    fun removeAddressInfo(address: String) {
        val intent: Intent = Intent(this@AddressListActivity,
            MainActivity::class.java)
        //TODO: 削除されたAddressがDefault Address なら更新する
//        mDefaultAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
//            if(addressInfo != null)
//            {
//                Completable.fromAction { mDefaultAddressViewModel.defaultAddressDelete(address as String) }
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.single())
//                    .subscribe()
//
//            }
//        })
        mAddressListViewModel.deleteAddress(address)
        startActivity(intent)
    }
}
