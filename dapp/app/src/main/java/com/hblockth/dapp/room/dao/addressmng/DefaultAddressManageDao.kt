package com.hblockth.dapp.room.dao.addressmng

import androidx.room.*
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DbAddressManage

@Dao
interface DefaultAddressManageDao {
    @Query("SELECT * FROM defaultaddress LIMIT 1")
    fun findDefaultAddressForLiveData(): LiveData<AddressModel>
    @Insert
    fun insertDefaultAddress(vararg address: DbAddressManage.DefaultAddressModel)
    @Delete
    fun delete(address: DbAddressManage.DefaultAddressModel)
}