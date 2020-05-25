package com.hblockth.dapp.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.models.addressmng.AddressModel

@Database(entities = arrayOf(AddressModel::class), version = 1)
abstract class AddressManageDatabase : RoomDatabase() {
    abstract fun addressManageDao(): AddressManageDao
}