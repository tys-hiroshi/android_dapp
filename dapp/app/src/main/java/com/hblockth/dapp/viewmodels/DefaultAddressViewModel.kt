package com.hblockth.dapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hblockth.dapp.repositories.AddressRepository
import com.hblockth.dapp.room.db.AppDatabase
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DbAddressManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultAddressViewModel(application: Application) : AndroidViewModel(application) {
    val addressModel: LiveData<DbAddressManage.DefaultAddressModel>
    val repository: AddressRepository

    init {
        repository = AddressRepository(
            AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao()
        )
        addressModel = repository.findDefaultAddressForLiveData()
    }

    //DBへレコード追加
    fun addDefaultAddress(addressStr: String) = viewModelScope.launch(Dispatchers.IO) {
        var defaultAddressModel = DbAddressManage.DefaultAddressModel(
            address = addressStr
        )
        repository.insert(defaultAddressModel)
    }

    fun newAddressInsert(addressStr: String) {
        addDefaultAddress(addressStr)
    }

}