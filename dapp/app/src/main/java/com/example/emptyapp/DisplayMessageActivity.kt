package com.example.emptyapp

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
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class DisplayMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        generateAddress()
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

    fun generateAddress() {
        val httpAsync = "https://bsvnodeapi.herokuapp.com/generateaddress/test"
            .httpGet()
            .responseObject<GenerateAddress> { request, response, result ->
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
                        val(generateAddress, err) = result
                        println("generateaddress:${generateAddress}")

                        val intent: Intent = getIntent()
                        val textViewAddress: TextView = findViewById(R.id.textViewAddress)
                        val textViewPrivateKeyWif: TextView = findViewById(R.id.textViewPrivateKeyWif)
                        textViewAddress.setText(generateAddress?.address)
                        textViewPrivateKeyWif.setText(generateAddress?.privatekey_wif)
//                        val editTextAddress: EditText = findViewById(R.id.editTextAddress) as EditText
//                        editTextAddress.setText(generateAddress?.address)
                        val address :String? = generateAddress?.address
                        createQRCode(address)
                    }
                }
            }
    }

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
                    }
                }
            }
    }
}
