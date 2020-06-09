package com.hblockth.dapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hblockth.dapp.repositories.DefaultAddressRepository
import com.hblockth.dapp.repositories.UploadTxIdRepository
import com.hblockth.dapp.room.db.AppDatabase
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DbAddressManage
import com.hblockth.dapp.room.models.addressmng.DefaultAddressModel
import com.hblockth.dapp.room.models.addressmng.UploadTxIdModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class UploadTxIdViewModelFactory(private val mApplication: Application, private val mParam: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UploadTxIdViewModel(mApplication, mParam) as T
    }
}

class UploadTxIdViewModel(application: Application, mParam: String) : AndroidViewModel(application) {
    val uploadTxIdModel: LiveData<List<UploadTxIdModel>>
    val repository: UploadTxIdRepository

    init {
        repository = UploadTxIdRepository(
            AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao()
        )
        uploadTxIdModel = repository.findUploadTxIdForLiveData(mParam)
    }

    //DBへレコード追加
    fun addUploadTxId(addressStr: String, txId: String) = viewModelScope.launch(Dispatchers.IO) {
        var uploadTxIdModel = UploadTxIdModel(
            address = addressStr,
            txid = txId,
            createdAt = Date()
        )
        repository.insertUploadTxId(uploadTxIdModel)
    }

    fun newUploadTxIdInsert(addressStr: String, txId: String) {
        addUploadTxId(addressStr, txId)
    }
}