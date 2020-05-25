package com.hblockth.dapp.room.models.addressmng

import androidx.room.Entity

class DbAddressManage {
}

//https://developer.android.com/training/data-storage/room/defining-data#kotlin
@Entity(tableName = "addresses", primaryKeys = arrayOf("address"))
data class AddressModel(
    val address: String
)