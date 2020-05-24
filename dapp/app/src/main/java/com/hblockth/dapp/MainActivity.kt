package com.hblockth.dapp
//package jp.co.casareal.fuel

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.gson.responseObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val EXTRA_MESSAGE: String = "com.hblockth.dapp.MESSAGE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val address: String = "mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy"
        setContentView(R.layout.activity_main)
    }

    /* Sendボタン押下時 */
    fun sendMessage(view: View) {
        val intent: Intent = Intent(this@MainActivity,
            DisplayMessageActivity::class.java)
        val editText: EditText = findViewById(R.id.editText) as EditText
        val address: String = "mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy"  //editText.text.toString()
        val args: Array<String> = arrayOf("green", "red", "blue")
        //val generateAddress : GenerateAddress? = main(args)
        //println("generateAddress:${generateAddress?.address}")
        //val result = getText("https://bsvnodeapi.herokuapp.com/generateaddress/test")
        //mgfPaFHyruQWVjHBks7rY9F3BbYrePvVAy
        //println(result)
        getbalance(address)
        //intent.putExtra(EXTRA_MESSAGE, message)
        //startActivity(intent)
    }

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
                            .setPositiveButton("OK", { dialog, which ->
                                // TODO:Yesが押された時の挙動
                                dialog.cancel()
                            })
                            .show()
                    }
                    is Result.Success -> {
                        val(getbalance,err) = result
                        println("getbalance:${getbalance}")
                        var bsvAmountSatoshi: Int? = getbalance?.confirmed
                        if(bsvAmountSatoshi == null){
                            bsvAmountSatoshi = 0
                        }
                        println(bsvAmountSatoshi)

//                        val edittextViewBsvAmount: EditText = findViewById(R.id.editTextAddress) as EditText
//                        edittextViewBsvAmount.setText(bsvAmount.toString())
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
