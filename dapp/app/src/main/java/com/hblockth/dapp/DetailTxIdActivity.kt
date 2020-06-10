package com.hblockth.dapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.hblockth.dapp.adapter.DownloadListViewAdapter
import com.hblockth.dapp.utils.Utils
import com.hblockth.dapp.viewmodels.DefaultAddressViewModel
import com.hblockth.dapp.viewmodels.DownloadListViewModel
import com.hblockth.dapp.viewmodels.DownloadListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class DetailTxIdActivity : AppCompatActivity() {
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent: Intent = getIntent()
        var txId: String? = intent.getStringExtra(Utils.SELECTED_TXID)
        getDownloadForTxId(txId as String)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tx_id)
    }

    fun getDownloadForTxId(txId: String){

        mDefaultAddressViewModel = ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
        mDefaultAddressViewModel.addressModel.observe(this, Observer { defaultaddressInfo ->
            if(defaultaddressInfo != null)
            {
//                //download data for txid
                  download(txId)
            }
        })
    }

    fun download(txId: String){
        val httpAsync = Utils.BNOTEAPI_API_DOWNLOAD
            .httpGet()
            .responseObject<String> { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println("failed request")
                        println(ex)
                        //val(generateAddress,err) = result
                        AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                            .setTitle("通信中にエラーが発生しました")
                            .setMessage("戻ってやり直してください。")
                            .setPositiveButton("OK", { dialog, which ->
                                // TODO:Yesが押された時の挙動
                                dialog.cancel()
                            })
                            .show()
                    }
                    is Result.Success -> {
                        val(data, err) = result
                        println("data:${data}")
                    }
                }
            }
    }
}