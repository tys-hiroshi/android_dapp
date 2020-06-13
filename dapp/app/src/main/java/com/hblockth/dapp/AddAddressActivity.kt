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
import com.hblockth.dapp.viewmodels.AddressViewModel
import com.hblockth.dapp.viewmodels.AddressViewModelFactory
import com.hblockth.dapp.viewmodels.DefaultAddressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddAddressActivity : AppCompatActivity() {
    private lateinit var buttonAddAddressInfo: Button
    private lateinit var mAddressViewModel: AddressViewModel
    private lateinit var mAddAddressViewModel: AddAddressViewModel
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        mAddAddressViewModel = ViewModelProviders.of(this).get(AddAddressViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        buttonAddAddressInfo = findViewById(R.id.buttonAddAddressInfo)
        buttonAddAddressInfo.setOnClickListener {

            var textInputEditTextAddress: TextView = findViewById(R.id.textInputEditTextAddress) as TextView
            val address = textInputEditTextAddress.text.toString()
            mAddressViewModel = ViewModelProviders.of(this, AddressViewModelFactory(this.application, address as String))
                .get<AddressViewModel>(
                    AddressViewModel::class.java
                )
            mAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
                if(addressInfo == null)
                {
                    onParallelClickAddButton()
                }
            })

            mDefaultAddressViewModel =  ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
            mDefaultAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
                if(addressInfo == null)
                {
                    var textInputEditTextAddress: TextView = findViewById(R.id.textInputEditTextAddress) as TextView
                    val address = textInputEditTextAddress.text
                    mDefaultAddressViewModel.newDefaultAddressInsert(address as String)
                }
            })
        }
    }

    fun onParallelClickAddButton() = GlobalScope.launch(
        Dispatchers.Main) {
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        async(Dispatchers.Default) {
            var textInputEditTextAddress: TextView = findViewById(R.id.textInputEditTextAddress) as TextView
            val address = textInputEditTextAddress.text.toString()
            var textInputEditTextPrivateKeyWif: TextView = findViewById(R.id.textInputEditTextPrivateKeyWif) as TextView
            val privateKeyWif = textInputEditTextPrivateKeyWif.text.toString()
            var textInputEditTextMnemonic: TextView = findViewById(R.id.editTextTextMultiLineMnemonic) as TextView
            val mnemonic = textInputEditTextMnemonic.text.toString()

            doAddAddressInfo(address, privateKeyWif, mnemonic)
        }.await().let {
        }
    }

    fun doAddAddressInfo(address: String, privateKeyWif: String, mnemonic:String){
        mAddAddressViewModel.newAddressInsert(address, privateKeyWif, mnemonic)
    }
}