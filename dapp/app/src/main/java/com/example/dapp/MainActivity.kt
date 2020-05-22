package com.hblockth.dapp
//package jp.co.casareal.fuel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.github.kittinunf.fuel.core.ResponseDeserializable

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.gson.responseObject

class MainActivity : AppCompatActivity() {
    val EXTRA_MESSAGE: String = "com.hblockth.dapp.MESSAGE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /* Sendボタン押下時 */
    fun sendMessage(view: View) {
        val intent: Intent = Intent(this@MainActivity,
            DisplayMessageActivity::class.java)
        val editText: EditText = findViewById(R.id.editText) as EditText
        val message: String = editText.text.toString()
        val args: Array<String> = arrayOf("green", "red", "blue")
        //val generateAddress : GenerateAddress? = main(args)
        //println("generateAddress:${generateAddress?.address}")
        //val result = getText("https://bsvnodeapi.herokuapp.com/generateaddress/test")
        //println(result)
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivity(intent)
    }

//    fun getText(url: String): String {
//        val (_, _, result) = url.httpGet().responseString()
//
//        return when (result) {
//            is Result.Failure -> {
//                println("failed")
//                val ex = result.getException()
//                println(ex.toString())
//                ex.toString()
//            }
//            is Result.Success -> {
//                result.get()
//            }
//        }
//    }

//    //https://www.yuulinux.tokyo/15220/
//    data class GenerateAddress (
//        var address: String,
//        var privatekey_wif: String
//    )
    
//    fun main(args: Array<String>) : GenerateAddress? {
////        "https://www.yuulinux.tokyo/demo/bs-tender-server-mock/test.json".httpGet().responseObject<User> { req, res, result ->
////            val(user,err) = result
////            println("user:${user}")
////        }
//        val generateAddress: GenerateAddress = GenerateAddress("", "")
//        val httpAsync = "https://bsvnodeapi.herokuapp.com/generateaddress/test"
//            .httpGet()
//            .responseObject<GenerateAddress> { request, response, result ->
//                when (result) {
//                    is Result.Failure -> {
//                        //val ex = result.getException()
//                        //println("failed request")
//                        //println(ex)
//                        //val(generateAddress,err) = result
//                    }
//                    is Result.Success -> {
////                        val data = result.get()
////                        val json = result.value.obj()
////                        val results =json.get("results") as JSONArray
////                        println("success request")
////                        println(data)
//                        val(generateAddress,err) = result
//                        println("aaaagenerateaddress:${generateAddress}")
//                        generateAddress
//                    }
//                }
//            }
//
//        //httpAsync.join()
//        println(generateAddress.address)
//        return null//httpAsync
//    }

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
