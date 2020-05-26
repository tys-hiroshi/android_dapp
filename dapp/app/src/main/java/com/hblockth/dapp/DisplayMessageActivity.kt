package com.hblockth.dapp

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipData.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.zxing.BarcodeFormat
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.db.AppDatabase
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.journeyapps.barcodescanner.BarcodeEncoder

class DisplayMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        generateMnemonic("test")
        // Activity開始時にIntentを取得し、文字列をセットする
    }

    //https://www.yuulinux.tokyo/15220/
    data class GenerateAddress (
        var address: String,
        var privatekey_wif: String
    )

    fun createQRCode(address: String?){
        try {
            val data : String = "${address}"
            val size : Int = 500
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, size, size)
            val imageQr = findViewById<ImageView>(R.id.qrImageView)
            imageQr.setImageBitmap(bitmap)
        } catch (e: Exception) {

        }
    }

    /* Copyボタン押下時 */
    fun copyClipboardAddress(view: View) {
        //クリップボードのサービスのインスタンスを取得する
        var mManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var textViewAddress: TextView = findViewById(R.id.textViewAddress) as TextView
        var clip: ClipData = newPlainText("", textViewAddress.text)
        mManager.setPrimaryClip(clip)
    }

    //camera same dice skull struggle guard jar media width shine wool top
    //https://faucet.bitcoincloud.net/
    //mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy
    fun generateAddress(mnemonic: String?) {
//        val httpAsync = "https://bsvnodeapi.herokuapp.com/generateaddress/test"
////            .httpGet()
////            .responseObject<GenerateAddress> { request, response, result ->
////                when (result) {
////                    is Result.Failure -> {
////                        val ex = result.getException()
////                        println("failed request")
////                        println(ex)
////                        //val(generateAddress,err) = result
////                        AlertDialog.Builder(this) // FragmentではActivityを取得して生成
////                            .setTitle("通信中にエラーが発生しました")
////                            .setMessage("戻ってやり直してください。")
////                            .setPositiveButton("OK", { dialog, which ->
////                                // TODO:Yesが押された時の挙動
////                                dialog.cancel()
////                            })
////                            .show()
////                    }
////                    is Result.Success -> {
////                        val(generateAddress, err) = result
////                        println("generateaddress:${generateAddress}")
////
////                        val intent: Intent = getIntent()
////                        val textViewAddress: TextView = findViewById(R.id.textViewAddress)
////                        val textViewPrivateKeyWif: TextView = findViewById(R.id.textViewPrivateKeyWif)
////                        textViewAddress.setText(generateAddress?.address)
////                        textViewPrivateKeyWif.setText(generateAddress?.privatekey_wif)
//////                        val editTextAddress: EditText = findViewById(R.id.editTextAddress) as EditText
//////                        editTextAddress.setText(generateAddress?.address)
////                        val address :String? = generateAddress?.address
////                        createQRCode(address)
////                    }
////                }
////            }
        // POSTによるHTTP通信
        val bnoteapiBaseUrl: String = "https://bnoteapi.herokuapp.com/v1"
        val api_mnemonic = "/api/mnemonic"
        //https://sendgrid.kke.co.jp/blog/?p=8471
        var content = """
        {
            "mnemonic": "${mnemonic}"
        }
        """
        println(content)
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json", "x-api-key" to "aaa")
        var url:String = "${bnoteapiBaseUrl}${api_mnemonic}"
        var httpPostAsync = url
            .httpPost()
            .jsonBody(content)
            .responseObject<ResponseGenerateAddress> { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println("failed request")
                            println(ex)
                            //val(generateAddress,err) = result
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
                            val(generateAddress, err) = result
                            println("generateAddress:${generateAddress}")

                            val intent: Intent = getIntent()
                            val textViewAddress: TextView = findViewById(R.id.textViewAddress)
                            textViewAddress.setText(generateAddress?.address)
                            val textViewPrivateKeyWif: TextView = findViewById(R.id.textViewPrivateKeyWif)
                            textViewPrivateKeyWif.setText(generateAddress?.privatekey_wif)
                            var address:String? = generateAddress?.address
                            createQRCode(address)
//                            val db = Room.databaseBuilder(
//                                applicationContext,
//                                AppDatabase::class.java, "database-dapp"
//                            ).build()
                            //val addressManageDao: AddressManageDao = db.addressManageDao()
//                            var addressList : MutableList<AddressModel> = mutableListOf()
//                            addressList.add(AddressModel(generateAddress?.address as String))

                            //addressManageDao.insert(AddressModel(generateAddress?.address as String))
                        }
                    }
            }
    }

    //https://www.yuulinux.tokyo/15220/
    data class RequestGenerateAddress (
        var mnemonic: String
    )

    data class ResponseGenerateAddress (
        var address: String,
        var balance_satoshi: Int,
        var code: Int,
        var privatekey_wif: String
    )

    //https://www.yuulinux.tokyo/15220/
    data class GenerateMnemonic (
        var mnemonic: String,
        var priv: String
    )
    fun generateMnemonic(network: String) {
        val httpAsync = "https://bsvnodeapi.herokuapp.com/gen_mnemonic/${network}"
            .httpGet()
            .responseObject<GenerateMnemonic> { request, response, result ->
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
                        val(generateMnemonic, err) = result
                        println("generateMnemonic:${generateMnemonic}")

                        val intent: Intent = getIntent()
                        val editTextAddress: EditText = findViewById(R.id.editTextAddress) as EditText
                        editTextAddress.setText(generateMnemonic?.mnemonic)
                        generateAddress(generateMnemonic?.mnemonic)
                    }
                }
            }
    }
}
