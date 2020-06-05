package com.hblockth.dapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.hblockth.dapp.requests.MultipartStringRequest
import com.hblockth.dapp.utils.Utils


class FileUploadActivity : AppCompatActivity() {
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null
    private val postURL: String = "https://ptsv2.com/t/54odo-1576291398/post" // remember to use your own api

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

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
            uploadImage("cP18Z8qwwjW8qTwSGTyhYuhUt6jmfPUfEowmhb8ymHx5URrVZx9V")
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

    private fun uploadImage(privateKeyWif: String){
        val request = MultipartStringRequest(
            Utils.BNOTEAPI_API_UPLOAD,
            Response.Listener<String?> {
                // do something
            },
            Response.ErrorListener {
                // do something
            }
        )
        val textParams: MutableMap<String, String> = HashMap()
        textParams["privatekey_wif"] = privateKeyWif
        request.setTextParams(textParams)
        val binaryParams: MutableMap<String, String> = HashMap()
        binaryParams["file"] = "/path/to/file.jpeg"
        request.setBinaryParams(binaryParams)
    }
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
