package com.hblockth.dapp.room.dao.addressmng

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Query("DELETE FROM defaultaddress")
    fun deleteDefaultAddressAll()

    //https://stackoverflow.com/questions/53344456/room-delete-executes-after-i-insert-new-values/53345937
    @Transaction
    open fun deleteAndCreate(vararg address: DefaultAddressModel): Unit {
        deleteDefaultAddressAll()
        insertDefaultAddress(*address)
    }

}