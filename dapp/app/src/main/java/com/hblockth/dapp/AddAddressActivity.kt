package com.hblockth.dapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beust.klaxon.Klaxon
import com.hblockth.dapp.model.ResponseBnoteApiUpload
import com.hblockth.dapp.viewmodels.AddAddressViewModel
import com.hblockth.dapp.viewmodels.DefaultAddressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddAddressActivity : AppCompatActivity() {
    private lateinit var buttonAddAddressInfo: Button
    private lateinit var mAddAddressViewModel: AddAddressViewModel
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        mAddAddressViewModel = ViewModelProviders.of(this).get(AddAddressViewModel::class.java)
        mDefaultAddressViewModel =  ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        buttonAddAddressInfo = findViewById(R.id.buttonAddAddressInfo)
        buttonAddAddressInfo.setOnClickListener {
            onParallelClickAddButton()
        }
    }
    fun onParallelClickAddButton() = GlobalScope.launch(
        Dispatchers.Main) {
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        async(Dispatchers.Default) {
            clickAddButton()
        }.await().let {

        }
    }
    fun clickAddButton(){
        //var textInputEditTextAddress: TextView = findViewById(R.id.textInputEditTextAddress) as TextView
//        var textInputEditTextAddress: TextView = findViewById(R.id.textInputEditTextAddress) as TextView
//        val address = textInputEditTextAddress.text
//        var textInputEditTextPrivateKeyWif: TextView = findViewById(R.id.textInputEditTextPrivateKeyWif) as TextView
//        val privateKeyWif = textInputEditTextPrivateKeyWif.text
//        var textInputEditTextMnemonic: TextView = findViewById(R.id.editTextTextMultiLineMnemonic) as TextView
//        val mnemonic = textInputEditTextMnemonic.text
        val address = "msXVAaciVB43mH6hd9ceDSQYkDZPAtVxMu"
        val privateKeyWif = "cTS3iAaeY4ipUUmKtTdD9K4MHMoTfpWbkFW7eMPtpCtdBZByXqiq"
        val mnemonic = "series evoke lawn shine walnut share curve street angle runway eager valve"
        mAddAddressViewModel.newAddressInsert(address as String, privateKeyWif as String, mnemonic as String)
        //TODO: 新しいAddressを生成するときにdefaultaddress テーブルに追加する。既にレコードがある場合は、何もしない。
//        mDefaultAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
//            if(addressInfo == null)
//            {
//                mDefaultAddressViewModel.newDefaultAddressInsert(address as String)
//            }
//        })
    }
}