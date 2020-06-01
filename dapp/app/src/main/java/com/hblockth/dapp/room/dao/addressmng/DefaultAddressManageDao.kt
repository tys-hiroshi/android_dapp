package com.hblockth.dapp.room.dao.addressmng

import androidx.room.*
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DbAddressManage
import com.hblockth.dapp.room.models.addressmng.DefaultAddressModel

@Dao
interface DefaultAddressManageDao {
    @Query("SELECT * FROM defaultaddress LIMIT 1")
    fun findDefaultAddressForLiveData(): LiveData<AddressModel>
    @Insert
    fun insertDefaultAddress(vararg address: DefaultAddressModel)
    @Delete
    fun delete(address: DefaultAddressModel)
}