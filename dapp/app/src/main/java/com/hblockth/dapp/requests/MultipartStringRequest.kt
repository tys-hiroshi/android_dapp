package com.hblockth.dapp.requests

import com.android.volley.AuthFailureError
import com.android.volley.Response

import com.android.volley.toolbox.StringRequest
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException


class MultipartStringRequest(
    url: String?,
    listener: Response.Listener<String?>?,
    errorListener: Response.ErrorListener?
) :
    StringRequest(Method.POST, url, listener, errorListener) {
    private val boundary = "----boundary" + System.currentTimeMillis()

    // テキストパラメータ nameとvalue
    private var textParams: Map<String, String> = HashMap()

    // バイナリパラメータ nameとファイルパス
    private var binaryParams: Map<String, String> = HashMap()
    override fun getBodyContentType(): String {
        return "multipart/form-data;boundary=$boundary"
    }

    @Throws(AuthFailureError::class)
    override fun getBody(): ByteArray {
        val bos = ByteArrayOutputStream()
        val dos = DataOutputStream(bos)
        try {
            writeTextPart(dos)
            writeBinaryPart(dos)
            dos.writeBytes("--$boundary--$CRLF")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bos.toByteArray()
    }

    @Throws(IOException::class)
    private fun writeTextPart(dos: DataOutputStream) {
        for ((key, value) in textParams) {
            dos.write(encodeToByteArray("Content-Disposition: form-data; name=\"$key\"$CRLF"))
            dos.writeBytes(CRLF)
            dos.write(encodeToByteArray(value + CRLF))
            dos.writeBytes("--$boundary$CRLF")
        }
    }

    @Throws(IOException::class)
    private fun writeBinaryPart(dos: DataOutputStream) {
        for ((key, value) in binaryParams) {
            val uploadFile = UploadFile(value)
            dos.write(encodeToByteArray("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + uploadFile.name + "\"" + CRLF))
            dos.writeBytes(
                "Content-Type: " + uploadFile.mimeType
                    .toString() + CRLF
            )
            dos.writeBytes(CRLF)
            dos.write(uploadFile.byteArray)
            dos.writeBytes(CRLF)
            dos.writeBytes(CRLF)
            dos.writeBytes("--$boundary$CRLF")
        }
    }

    fun setTextParams(textParams: Map<String, String>) {
        this.textParams = textParams
    }

    fun setBinaryParams(binaryParams: Map<String, String>) {
        this.binaryParams = binaryParams
    }

    protected fun encodeToByteArray(str: String): ByteArray {
        return str.toByteArray()
    }

    companion object {
        private const val CRLF = "\r\n"
    }
}