package com.hblockth.dapp.room.dao.addressmng

import androidx.room.*
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DefaultAddressModel

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

    @Query("SELECT * FROM defaultaddress LIMIT 1")
    fun findDefaultAddressForLiveData(): LiveData<DefaultAddressModel>

//    @Insert
//    fun insertAll(vararg addresses: MutableList<AddressModel>)

    @Insert
    fun insert(vararg addresses: AddressModel)

    @Insert
    fun insertDefaultAddress(vararg address: DefaultAddressModel)

    @Update
    suspend fun updateDefaultAddress(vararg address: DefaultAddressModel)

    @Delete
    fun delete(address: AddressModel)

    @Delete
    fun deteDefaultAddress(vararg address: DefaultAddressModel)
}