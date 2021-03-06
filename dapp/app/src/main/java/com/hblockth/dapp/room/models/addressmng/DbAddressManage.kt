package com.hblockth.dapp.room.models.addressmng

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

class DbAddressManage {
}

//https://developer.android.com/training/data-storage/room/defining-data#kotlin
@Entity(tableName = "defaultaddress")
data class DefaultAddressModel(
    @PrimaryKey val address: String
)

//https://developer.android.com/training/data-storage/room/defining-data#kotlin
@Entity(tableName = "addresses")
data class AddressModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var address: String,
    var privateKeyWif: String,
    var mnemonic: String,
    var createdAt: Date
)


//upload txid
@Entity(tableName = "uploadtxids")
data class UploadTxIdModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var address: String,
    var txid: String,
    var createdAt: Date
)