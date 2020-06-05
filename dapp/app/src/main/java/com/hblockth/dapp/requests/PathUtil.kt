package com.hblockth.dapp.requests

import java.io.File

object PathUtil {
    fun getFileName(path: String?): String {
        return File(path).getName()
    }

    fun getExtension(path: String): String? {
        var extension: String? = null
        val index = path.lastIndexOf(".")
        if (index > 0) {
            extension = path.substring(index + 1)
        }
        return extension
    }
}