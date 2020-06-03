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
import java.text.SimpleDateFormat
import java.util.*


//import com.hblockth.dapp.databinding.ActivityAddressListBinding

class AddressListActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityAddressListBinding
    private lateinit var mViewModel: AddressListViewModel
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    private lateinit var mAdapter: AddressViewAdapter
    private var hasScrolledSteep = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
//        val binding : ActivityAddressListBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_address_list)

        mViewModel = ViewModelProviders.of(this).get(AddressListViewModel::class.java)
        mDefaultAddressViewModel =  ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
        mAdapter = AddressViewAdapter(emptyList())
        //テストデータの生成
        //https://qiita.com/fu_neko/items/722e0ab5f0f1255f87bf
        val date = SimpleDateFormat("yyyy/MM/dd").format(Date())
        //val addressList = mutableListOf<AddressModel>()

//        val db = Room.databaseBuilder(
//            applicationContext,
//            AddressManageDatabase::class.java, "database-dapp"
//        ).build()
//        val addressManageDao: AddressManageDao = DApplication.db.addressManageDao()
//        val addresses:List<com.hblockth.dapp.room.models.addressmng.AddressModel> = addressManageDao.getAll()

//        for (item in addressList){
//            addressList.add(AddressModel(item.address, "2020/05/10"))
//        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_address_list)
        mBinding.viewModel = mViewModel
        mBinding.addressRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@AddressListActivity)
        }
        mViewModel.addresses.observe(this, Observer { addressList ->
            mAdapter.addressList = addressList
            mAdapter.notifyDataSetChanged()
            hasScrolledSteep = true
        })
        //RecyclerViewにAdapterとLayoutManagerを設定

//        findViewById<RecyclerView>(R.id.addressRecyclerView).also { recyclerView: RecyclerView ->
//            recyclerView.adapter = AddressViewAdapter(this, )
//            recyclerView.layoutManager = LinearLayoutManager(this)
//        }

        // インターフェースの実装
        mAdapter.setOnItemClickListener(object:AddressViewAdapter.OnItemClickListener{
            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                changeAddress(clickedText)
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
}
