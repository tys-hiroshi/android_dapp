package com.hblockth.dapp.requests

import android.webkit.MimeTypeMap
import java.io.File
import java.io.IOException


internal class UploadFile(private val filePath: String) {
    val name: String
        get() = PathUtil.getFileName(filePath)

    val mimeType: String?
        get() {
            val mime = MimeTypeMap.getSingleton()
            val extension: String = PathUtil.getExtension(filePath) as String
            return if (extension != null) mime.getMimeTypeFromExtension(extension) else null
        }

    @get:Throws(IOException::class)
    val byteArray: ByteArray
        get() = FileUtil.readAsByteArray(File(filePath))

}