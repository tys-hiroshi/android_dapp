package com.hblockth.dapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hblockth.dapp.repositories.UploadTxIdRepository
import com.hblockth.dapp.room.db.AppDatabase
import com.hblockth.dapp.room.models.addressmng.UploadTxIdModel

class DownloadListViewModelFactory(private val mApplication: Application, private val address: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DownloadListViewModel(mApplication, address) as T
    }
}

class DownloadListViewModel(application: Application, address: String) : AndroidViewModel(application) {
    val uploadTxIdModel: LiveData<List<UploadTxIdModel>>
    val repository: UploadTxIdRepository

    init {
        repository = UploadTxIdRepository(
            AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao()
        )
        uploadTxIdModel = repository.findUploadTxIdForLiveData(address)
    }
}