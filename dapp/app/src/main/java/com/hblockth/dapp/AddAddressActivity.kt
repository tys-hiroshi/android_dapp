package com.hblockth.dapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hblockth.dapp.viewmodels.AddAddressViewModel
import com.hblockth.dapp.viewmodels.DefaultAddressViewModel

class AddAddressActivity : AppCompatActivity() {

    private lateinit var mAddAddressViewModel: AddAddressViewModel
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        mAddAddressViewModel = ViewModelProviders.of(this).get(AddAddressViewModel::class.java)
        mDefaultAddressViewModel =  ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
    }

    fun clickAddButton(){
        //var textInputEditTextAddress: TextView = findViewById(R.id.textInputEditTextAddress) as TextView
        var textInputEditTextAddress: TextView = findViewById(R.id.textInputEditTextAddress) as TextView
        val address = textInputEditTextAddress.text
        var textInputEditTextPrivateKeyWif: TextView = findViewById(R.id.textInputEditTextPrivateKeyWif) as TextView
        val privateKeyWif = textInputEditTextPrivateKeyWif.text
        var textInputEditTextMnemonic: TextView = findViewById(R.id.editTextTextMultiLineMnemonic) as TextView
        val mnemonic = textInputEditTextMnemonic.text
        mAddAddressViewModel.newAddressInsert(address as String, privateKeyWif as String, mnemonic as String)
        //TODO: 新しいAddressを生成するときにdefaultaddress テーブルに追加する。既にレコードがある場合は、何もしない。
        mDefaultAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
            if(addressInfo == null)
            {
                mDefaultAddressViewModel.newDefaultAddressInsert(address as String)
            }
        })
    }
}