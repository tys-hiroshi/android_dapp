package com.hblockth.dapp.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.models.addressmng.AddressModel

//https://github.com/jianastrero/room-background-example/blob/b4d9505c489cfc406c74f613d18fbc372c5c7f48/app/src/main/java/com/jianastrero/roombackgroundexample/repositories/MessageRepository.kt

class AddressRepository(
    private val addressManageDao: AddressManageDao
) {
    fun findAllForLiveData(): LiveData<List<AddressModel>> = addressManageDao.getAllForLiveData()

    fun findAll(): List<AddressModel> = addressManageDao.getAll()

    @WorkerThread
    suspend fun insert(vararg address: AddressModel) {
        addressManageDao.insert(*address)
    }
}