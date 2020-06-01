package com.hblockth.dapp.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.dao.addressmng.DefaultAddressManageDao
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DbAddressManage

//https://github.com/jianastrero/room-background-example/blob/b4d9505c489cfc406c74f613d18fbc372c5c7f48/app/src/main/java/com/jianastrero/roombackgroundexample/repositories/MessageRepository.kt

class DefaultAddressRepository(
    private val defaultAddressManageDao: DefaultAddressManageDao
) {
    //NOTE: Get Default Address from defaultaddress table
    fun findDefaultAddressForLiveData(address : String): LiveData<AddressModel> = defaultAddressManageDao.findDefaultAddressForLiveData()

    @WorkerThread
    suspend fun insertDefaultAddress(vararg address: DbAddressManage.DefaultAddressModel) {
        defaultAddressManageDao.insertDefaultAddress(*address)
    }

}