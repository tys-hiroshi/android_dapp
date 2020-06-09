package com.hblockth.dapp.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.models.addressmng.DefaultAddressModel
import com.hblockth.dapp.room.models.addressmng.UploadTxIdModel

class UploadTxIdRepository(
    private val addressManageDao: AddressManageDao
) {
    fun findUploadTxIdForLiveData(address: String): LiveData<List<UploadTxIdModel>> = addressManageDao.findUploadTxIdByAddressForLiveData(address)

    @WorkerThread
    suspend fun insertUploadTxId(vararg uploadTxIdData: UploadTxIdModel) {
        addressManageDao.insertUploadTxId(*uploadTxIdData)
    }

}