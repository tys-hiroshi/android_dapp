package com.hblockth.dapp.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.models.addressmng.DefaultAddressModel

//https://github.com/jianastrero/room-background-example/blob/b4d9505c489cfc406c74f613d18fbc372c5c7f48/app/src/main/java/com/jianastrero/roombackgroundexample/repositories/MessageRepository.kt

class DefaultAddressRepository(
    private val addressManageDao: AddressManageDao
) {
    //NOTE: Get Default Address from defaultaddress table
    fun findDefaultAddressForLiveData(): LiveData<DefaultAddressModel> = addressManageDao.findDefaultAddressForLiveData()

    @WorkerThread
    suspend fun insertDefaultAddress(vararg address: DefaultAddressModel) {
        addressManageDao.insertDefaultAddress(*address)
    }

    @WorkerThread
    suspend fun updateDefaultAddress(vararg address: DefaultAddressModel) {
        addressManageDao.updateDefaultAddress(*address)
    }

    @WorkerThread
    fun deleteDefaultAddress(vararg address: DefaultAddressModel) {
        addressManageDao.deleteAndCreate(*address)
    }

}