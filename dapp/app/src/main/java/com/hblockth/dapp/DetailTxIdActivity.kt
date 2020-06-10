package com.hblockth.dapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.hblockth.dapp.utils.Utils
import com.hblockth.dapp.viewmodels.DefaultAddressViewModel
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream

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
        //txid: 03111ca5e2570904b8c81a982aa491494ce8621b0fd9bbb1e2afe6e2791ca1c7
        val txId = "03111ca5e2570904b8c81a982aa491494ce8621b0fd9bbb1e2afe6e2791ca1c7"
//        Fuel.download("http://httpbin.org/bytes/32768")
//            .header(mapOf("x-api-key" to "apikey0"))
//            .destination { response, url ->
//                File.createTempFile("temp", ".tmp")
//            }
        val httpAsync = "${Utils.BNOTEAPI_API_DOWNLOAD}/${txId}"
            .httpGet()
            .header("x-api-key", "apikey0")
            .response() { request, response, result ->
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
                        var outputFilename = "${Environment.getExternalStorageDirectory()}/Download/download.jpg"
                        File(outputFilename).writeBytes(data as ByteArray)
//                        val file = File("download.jpg")
//                        val dis = DataInputStream(FileInputStream(file))
//                        dis.readFully(data)
//                        dis.close()
                        println("data:${data}")
                    }
                }
            }
    }
}