package com.hblockth.dapp
//package jp.co.casareal.fuel

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.hblockth.dapp.adapter.AddressViewAdapter
import com.hblockth.dapp.utils.Utils
import com.hblockth.dapp.viewmodels.AddressViewModel
import com.hblockth.dapp.viewmodels.AddressViewModelFactory
import com.hblockth.dapp.viewmodels.DefaultAddressViewModel
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: AddressViewModel
    private lateinit var mDefaultAddressViewModel: DefaultAddressViewModel
    private lateinit var mAdapter: AddressViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Get default address info
        getDefaultAddressInfo()
        val addressTextView: TextView = findViewById(R.id.AddressTextView) as TextView
        val address:String = addressTextView.text.toString()
        // Activity開始時にIntentを取得し、文字列をセットする
//        val intent: Intent = getIntent()
//        var address: String? = intent.getStringExtra(Utils.SELECTED_ADDRESS)
//        if(address == null){
//            address = "mg8atoeAz2b9dVjm6n6sACCjRb6fMc6kgs"  //"mnLKtTcMnmhchza8wPMLuk813j36sEpArK"  //"mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy"
//        }
        getbalance(address)
        setAddressInfo(address)

        setContentView(R.layout.activity_main)
    }

    fun createAddress(view: View) {
        val intent: Intent = Intent(this@MainActivity,
            DisplayMessageActivity::class.java)
        startActivity(intent)
    }

    fun viewAddressList(view: View) {
        val intent: Intent = Intent(this@MainActivity,
            AddressListActivity::class.java)
        startActivity(intent)
    }

    fun setAddressInfo(address: String?){
        mViewModel = ViewModelProviders.of(this, AddressViewModelFactory(this.application, address as String))
            .get<AddressViewModel>(
                AddressViewModel::class.java
            )
        mDefaultAddressViewModel =  ViewModelProvider.NewInstanceFactory().create(DefaultAddressViewModel::class.java)
        print(mViewModel.addressModel)
        mViewModel.addressModel.observe(this, Observer { addressInfo ->
//
//            println("address")
//            println(addressInfo)
            if(addressInfo != null)
            {
                val addressTextView: TextView = findViewById(R.id.AddressTextView)
                addressTextView.setText(addressInfo.address)
                val privateKeyWifTextView: TextView = findViewById(R.id.PrivateKeyWifMultilineText)
                privateKeyWifTextView.setText(addressInfo.privateKeyWif)
                val mnemonicMultilineText: TextView = findViewById(R.id.MnemonicMultilineText)
                mnemonicMultilineText.setText(addressInfo.mnemonic)
            }
        })
        //mViewModel = ViewModelProviders.of(this, AddressViewModel.AddressViewModelFactory(application, address)).get(AddressViewModel::class.java)
        //mViewModel =  ViewModelProviders.of(this).get(AddressViewModel::class.java)//ViewModelProvider.NewInstanceFactory().create(AddressViewModel::class.java)

//        val addressInfo: AddressModel = mViewModel.getAddressSecretInfo(address as String)
        //mViewModel.getAddressSecretInfo(address as String)
        //val addressInfo: AddressModel = mViewModel.addressModel
        //setText
    }

    /* Sendボタン押下時 */
//    fun sendMessage(view: View) {
//        val intent: Intent = Intent(this@MainActivity,
//            DisplayMessageActivity::class.java)
//        val editText: EditText = findViewById(R.id.editText) as EditText
//        val address: String = "mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy"  //editText.text.toString()
//        val args: Array<String> = arrayOf("green", "red", "blue")
//        //val generateAddress : GenerateAddress? = main(args)
//        //println("generateAddress:${generateAddress?.address}")
//        //val result = getText("https://bsvnodeapi.herokuapp.com/generateaddress/test")
//        //mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy
//        //println(result)
//        //intent.putExtra(EXTRA_MESSAGE, message)
//        //startActivity(intent)
//    }

    //https://www.yuulinux.tokyo/15220/
    data class GetBalance (
        var confirmed: Int,
        var unconfirmed: Int
    )
    
    fun getbalance(address: String) : GetBalance? {
        val generateAddress: GetBalance = GetBalance(0, 0)
        val httpAsync = "https://bnoteapi.herokuapp.com/v1/api/getbalance/${address}"
            .httpGet()
            .header(hashMapOf("x-api-key" to "apikey"))
            .responseObject<GetBalance> { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                            .setTitle("通信中にエラーが発生しました")
                            .setMessage("戻ってやり直してください。")
                            .setPositiveButton("OK") { dialog, which ->
                                // TODO:Yesが押された時の挙動
                                dialog.cancel()
                            }
                            .show()
                    }
                    is Result.Success -> {
                        val(getbalance,err) = result
                        println("getbalance:${getbalance}")
                        var bsvAmountSatoshi: Int? = getbalance?.confirmed
                        var unconfirmedBsvAmountSatoshi: Int? = getbalance?.unconfirmed
                        if(bsvAmountSatoshi == null){
                            bsvAmountSatoshi = 0
                        }
                        if(unconfirmedBsvAmountSatoshi == null){
                            unconfirmedBsvAmountSatoshi = 0
                        }
                        println(bsvAmountSatoshi)
                        println(unconfirmedBsvAmountSatoshi)

//                        val edittextViewBsvAmount: EditText = findViewById(R.id.editTextAddress) as EditText
//                        edittextViewBsvAmount.setText(bsvAmount.toString())
                        bsvAmountSatoshi += unconfirmedBsvAmountSatoshi
                        var bsvAmountSatoshiDouble : Double = bsvAmountSatoshi.toDouble()
                        var satoshiUnit: Double = 100000000.toDouble()
                        val textViewBsvAmount: TextView = findViewById(R.id.textViewBsvAmount)
                        var bsvAmount: Double? = (bsvAmountSatoshiDouble / satoshiUnit).toDouble()
                        println(bsvAmount)
                        var bsvAmountStr: String = bsvAmount.toString()
                        println(bsvAmountStr)
                        textViewBsvAmount.setText(bsvAmountStr)
                        //textViewBsvAmount.setText(bsvAmount)
                    }
                }
            }

        //httpAsync.join()
        //println(getbalance?.confirmed)
        return null//httpAsync
    }

    /* Copyボタン押下時 */
    fun copyClipboardAddress(view: View) {
        //クリップボードのサービスのインスタンスを取得する
        var mManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var addressTextView: TextView = findViewById(R.id.AddressTextView) as TextView
        var clip: ClipData = ClipData.newPlainText("addressText", addressTextView.text)
        mManager.setPrimaryClip(clip)
    }

    /* Copyボタン押下時 */
    fun copyClipboardPrivateKeyWif(view: View) {
        //クリップボードのサービスのインスタンスを取得する
        var mManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var privateKeyWifTextView: TextView = findViewById(R.id.PrivateKeyWifMultilineText) as TextView
        var clip: ClipData = ClipData.newPlainText("privateKeyWifText", privateKeyWifTextView.text)
        mManager.setPrimaryClip(clip)
    }

    /* Copyボタン押下時 */
    fun copyClipboardMnemonic(view: View) {
        //クリップボードのサービスのインスタンスを取得する
        var mManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var mnemonicMultilineText: TextView = findViewById(R.id.MnemonicMultilineText) as TextView
        var clip: ClipData = ClipData.newPlainText("mnemonicText", mnemonicMultilineText.text)
        mManager.setPrimaryClip(clip)
    }

    //https://monoworks.co.jp/post/android_develop_memo_2015111601/
    fun displayMnemonic(view: View) {
        //TODO: displayボタンを押したら、表示・非表示にする
//        var mnemonicMultilineText: TextView = findViewById(R.id.MnemonicMultilineText) as TextView
//        if(mnemonicMultilineText.inputType == InputType.TYPE_CLASS_TEXT){
//            mnemonicMultilineText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
//        }else{
//            mnemonicMultilineText.inputType = InputType.TYPE_CLASS_TEXT
//        }
    }

    fun getDefaultAddressInfo() {
        mDefaultAddressViewModel =  ViewModelProvider.NewInstanceFactory().create(DefaultAddressViewModel::class.java)
        //print(mViewModel.addressModel)
        mDefaultAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
            if(addressInfo != null)
            {
                val addressTextView: TextView = findViewById(R.id.AddressTextView)
                addressTextView.setText(addressInfo.address)
            }
        })
    }

//    fun main(args: Array<String>) {
//
//        // 非同期処理
//        val baseUrl = "https://bsvnodeapi.herokuapp.com"
//        val generateAddressTest = baseUrl + "/generateaddress/test"
//        "https://bsvnodeapi.herokuapp.com/generateaddress/test".httpGet().response { request, response, result ->
//            when (result) {
//                is Result.Success -> {
//                    // レスポンスボディを表示
//                    println("非同期処理の結果：" + String(response.data))
//                }
//                is Result.Failure -> {
//                    println("通信に失敗しました。")
//                }
//            }
//        }
//
//        // 同期処理
//        val triple = "https://bsvnodeapi.herokuapp.com/generateaddress/test".httpGet().response()
//        // レスポンスボディを表示
//        println("同期処理の結果：" + String(triple.second.data))
//
//        val triple1 = "https://www.casareal.co.jp/".httpGet().response()
//        // レスポンスボディを表示
//        println("同期処理の結果：" + String(triple1.second.data))
//    }
}
