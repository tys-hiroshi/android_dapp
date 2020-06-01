package com.hblockth.dapp.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DbAddressManage

//https://github.com/jianastrero/room-background-example/blob/b4d9505c489cfc406c74f613d18fbc372c5c7f48/app/src/main/java/com/jianastrero/roombackgroundexample/repositories/MessageRepository.kt

class AddressRepository(
    private val addressManageDao: AddressManageDao
) {
    fun findAllForLiveData(): LiveData<List<AddressModel>> = addressManageDao.getAllForLiveData()

    fun findAll(): List<AddressModel> = addressManageDao.getAll()

//    @WorkerThread
//    suspend fun findByAddress(address: String): LiveData<AddressModel> {
//        return addressManageDao.findByAddress(address)
//    }

    fun findByAddressForLiveData(address : String): LiveData<AddressModel> = addressManageDao.findByAddressForLiveData(address)

    fun findByAddress(address : String): AddressModel = addressManageDao.findByAddress(address)

    //NOTE: Get Default Address from defaultaddress table
    fun findDefaultAddressForLiveData(): LiveData<DbAddressManage.DefaultAddressModel> = addressManageDao.findDefaultAddressForLiveData()

    @WorkerThread
    suspend fun insert(vararg address: DbAddressManage.DefaultAddressModel) {
        addressManageDao.defaultinsert(*address)
    }
}