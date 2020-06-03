package com.hblockth.dapp.utils

import com.hblockth.dapp.room.models.utils.StringKeyValuePair
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    const val DATABASE_NAME = "database-dapp"
    const val SELECTED_ADDRESS = "com.hblockth.dapp.selected_address"
    const val MAX_RETRIES = 3L
    private const val INITIAL_BACKOFF = 2000L

    private var formatter = SimpleDateFormat("h:mm aa", Locale.getDefault())
    private var dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var dateTimeFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    /// Upload file
    private const val BNOTEAPI_BASEURL = "http://bnoteapi.herokuapp.com/v1"
    //ref http://bnoteapi.herokuapp.com/v1/ui/#/
    const val BNOTEAPI_API_UPLOAD = "${BNOTEAPI_BASEURL}/api/upload"

    private val df = DecimalFormat("###.#")

    fun getTimeString(timeInMillis: Long): String {
        return formatter.format(Date(timeInMillis * 1000))
    }

    fun getDateString(timeInMillis: Long): String {
        return dateFormatter.format(Date(timeInMillis * 1000))
    }

    fun getTemperature(temp: Double): String {
        return "${df.format(temp)}°C"
    }

    fun shouldCallApi(
        lastApiCallMillis: String,
        cacheThresholdInMillis: Long = 300000L //default value is 5 minutes//
    ): Boolean {
        return (System.currentTimeMillis() - lastApiCallMillis.toLong()) >= cacheThresholdInMillis
    }

    fun getCurrentTimeKeyValuePair(key: String): StringKeyValuePair {
        return StringKeyValuePair(key, System.currentTimeMillis().toString())
    }

    fun getBackoffDelay(attempt: Long) = INITIAL_BACKOFF * (attempt + 1)
}