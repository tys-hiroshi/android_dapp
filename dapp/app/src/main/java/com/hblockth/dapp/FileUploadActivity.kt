package com.hblockth.dapp

import android.R.attr
import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.hblockth.dapp.requests.MultipartStringRequest
import com.hblockth.dapp.utils.Utils
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException


class FileUploadActivity : AppCompatActivity() {
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null
    private val postURL: String = "https://ptsv2.com/t/54odo-1576291398/post" // remember to use your own api

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

    private var filePath: String = ""
    //https://medium.com/better-programming/how-to-upload-an-image-file-to-your-server-using-volley-in-kotlin-a-step-by-step-tutorial-23f3c0603ec2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_upload)
        imageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            launchGallery()
        }
        sendButton = findViewById(R.id.sendButton)
        sendButton.setOnClickListener {
            //uploadImage("cP18Z8qwwjW8qTwSGTyhYuhUt6jmfPUfEowmhb8ymHx5URrVZx9V")
            uploadImage("cP18Z8qwwjW8qTwSGTyhYuhUt6jmfPUfEowmhb8ymHx5URrVZx9V", filePath)
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

//    fun getDefaultAddressInfo() {
//        mDefaultAddressViewModel = ViewModelProviders.of(this).get(DefaultAddressViewModel::class.java)
//        //mDefaultAddressViewModel =  ViewModelProvider.NewInstanceFactory().create(DefaultAddressViewModel::class.java)
//        //print(mViewModel.addressModel)
//        mDefaultAddressViewModel.addressModel.observe(this, Observer { addressInfo ->
//            if(addressInfo != null)
//            {
//                uploadImage(addressInfo.address)
//            }
//        })
//    }
    private fun uploadImage(privateKeyWif: String, filePath: String) {

        //TODO: file upload
        //https://cpoint-lab.co.jp/article/201812/6921/
        //var filePath = getRealPathFromURI(uri)
        //uploadImage(privateKeyWif, filePath as String)
        val client: OkHttpClient = OkHttpClient().newBuilder().build()
        val mediaType: MediaType? = "text/plain; charset=utf-8".toMediaTypeOrNull();
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        //https://github.com/esafirm/android-salesapp/blob/96a6060dce5dcc501582658921390a160e7b489d/app/src/main/kotlin/com/arx/android/salesapp/data/model/place/PlacePostParameter.kt
        var multipartBody = builder.addFormDataPart("privatekey_wif", "privatekey_wif")
            .addFormDataPart(
                "file", filePath,
                File(filePath)
                    .asRequestBody("application/octet-stream".toMediaTypeOrNull())
            ).build()
    //                val body: RequestBody = Request.Builder()
    //                    .
    //                    .method("POST", requestBody)
    //                    .addFormDataPart("privatekey_wif", "privatekey_wif")
    //                    .addFormDataPart(
    //                        "file", "/C:/Users/Tashiro/Pictures/uniswap.jpg",
    //                        RequestBody.create(
    //                            MediaType.parse("application/octet-stream"),
    //                            File("/C:/Users/Tashiro/Pictures/uniswap.jpg")
    //                        )
    //                    )
    //                    .build()
        val request: Request = Request.Builder()
            .url("https://bnoteapi.herokuapp.com/v1/api/upload")
            .method("POST", multipartBody)
            .addHeader("x-api-key", "aaaaaaa")
            .build()
        val response = client.newCall(request).execute()
        println(response.message)
        // override if necessary
    }
//    @Throws(IOException::class)
//    private fun uploadImage(privateKeyWif: String, imageUri: String){
//        val request = MultipartStringRequest(
//            Utils.BNOTEAPI_API_UPLOAD,
//            Response.Listener<String?> {
//                // do something
//            },
//            Response.ErrorListener {
//                // do something
//            }
//        )
//        val textParams: MutableMap<String, String> = HashMap()
//        textParams["privatekey_wif"] = privateKeyWif
//        request.setTextParams(textParams)
//        val binaryParams: MutableMap<String, String> = HashMap()
//        binaryParams["file"] = imageUri.toString()
//        request.setBinaryParams(binaryParams)
//
//        Volley.newRequestQueue(this).add(request)
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            if (uri != null) {
                var privateKeyWif: String = "cUVRvm7zxnLxUQ6bgnoHD8XroXcsk6xMwhxMiqoZPaHY2bUW8wKc"
                filePath = getRealPathFromURI(uri) as String
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor =
            contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
//    private fun getPictPath(uri: Uri): String? {
//        val id = DocumentsContract.getDocumentId(uri)
//        val selection = "_id=?"
//        val selectionArgs =
//            arrayOf(id.split(":".toRegex()).toTypedArray()[1])
//        var file: File? = null
//        val cursor: Cursor? = contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            arrayOf(MediaStore.MediaColumns.DATA),
//            selection,
//            selectionArgs,
//            null
//        )
//        if (cursor != null && cursor.moveToFirst()) {
//            file = File(cursor.getString(0))
//        }
//        cursor?.close()
//        return if (file != null) {
//            file.getAbsolutePath()
//        } else null
//    }


//    private fun uploadImage(privatekeyWif: String) {
//        imageData?: return
//        val request = object : VolleyFileUploadRequest(
//            Method.POST,
//            Utils.BNOTEAPI_API_UPLOAD,  //postURL, //
//            Response.Listener {
//                println("response is: $it")
//            },
//            Response.ErrorListener {
//                println("error is: $it")
//            }
//        ) {
//            override fun getHeaders(): MutableMap<String, String> {
//                var params = HashMap<String, String>()
//                params["x-api-key"] = "apikey0"
//                return params
//            }
//            override fun getByteData(): MutableMap<String, String> {
//                var params = HashMap<String, String>()
//                //params["imageFile"] = FileDataPart("image", imageData!!, "jpeg")
//                params["file"] = imageData.toString()  //FileDataPart("image", imageData!!, "jpeg")
//                params["privatekeyWif"] = privatekeyWif
//                return params
//            }
//        }
//        Volley.newRequestQueue(this).add(request)
//    }
//
//    @Throws(IOException::class)
//    private fun createImageData(uri: Uri) {
//        val inputStream = contentResolver.openInputStream(uri)
//        inputStream?.buffered()?.use {
//            imageData = it.readBytes()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//            val uri = data?.data
//            if (uri != null) {
//                //imageView.setImageURI(uri)
//                createImageData(uri)
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
}
