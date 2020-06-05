package com.hblockth.dapp.requests

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

object FileUtil {
    @Throws(IOException::class)
    fun readAsByteArray(file: File): ByteArray {
        val length: Long = file.length()
        val bytes = ByteArray(length.toInt())
        val `is`: InputStream = FileInputStream(file)
        var offset = 0
        var numRead: Int = 0
        while (offset < bytes.size && `is`.read(bytes, offset, bytes.size - offset)
                .also({ numRead = it }) >= 0
        ) {
            offset += numRead
        }
        `is`.close()
        return bytes
    }
}