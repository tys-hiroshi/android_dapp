package com.hblockth.dapp.room.dao.addressmng

import androidx.room.*
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.models.addressmng.AddressModel

@Dao
interface AddressManageDao {
    @Query("SELECT * FROM addresses")
    fun getAllForLiveData(): LiveData<List<AddressModel>>

    @Query("SELECT * FROM addresses")
    fun getAll(): List<AddressModel>

    @Query("SELECT * FROM addresses ORDER BY address")
    fun getAllBlocking(): List<AddressModel>

    @Query("SELECT * FROM addresses WHERE address = :address LIMIT 1")
    fun findByAddressForLiveData(address: String): LiveData<AddressModel>

    @Query("SELECT * FROM addresses WHERE address = :address LIMIT 1")
    fun findByAddress(address: String): AddressModel

//    @Insert
//    fun insertAll(vararg addresses: MutableList<AddressModel>)

    @Insert
    fun insert(vararg addresses: AddressModel)

    @Delete
    fun delete(address: AddressModel)
}