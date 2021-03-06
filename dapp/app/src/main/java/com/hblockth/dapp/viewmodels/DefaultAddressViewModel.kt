package com.hblockth.dapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hblockth.dapp.repositories.DefaultAddressRepository
import com.hblockth.dapp.room.db.AppDatabase
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DbAddressManage
import com.hblockth.dapp.room.models.addressmng.DefaultAddressModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultAddressViewModel(application: Application) : AndroidViewModel(application) {
    val addressModel: LiveData<DefaultAddressModel>
    val repository: DefaultAddressRepository

    init {
        repository = DefaultAddressRepository(
            AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao()
        )
        addressModel = repository.findDefaultAddressForLiveData()
    }

    //DBへレコード追加
    fun addDefaultAddress(addressStr: String) = viewModelScope.launch(Dispatchers.IO) {
        var defaultAddressModel = DefaultAddressModel(
            address = addressStr
        )
        repository.insertDefaultAddress(defaultAddressModel)
    }

    fun newDefaultAddressInsert(addressStr: String) {
        addDefaultAddress(addressStr)
    }

    //DBのレコード更新
    fun modifyDefaultAddress(addressStr: String) = viewModelScope.launch(Dispatchers.IO) {
        var defaultAddressModel = DefaultAddressModel(
            address = addressStr
        )
        repository.updateDefaultAddress(defaultAddressModel)
    }

    fun defaultAddressUpdate(addressStr: String) {
        modifyDefaultAddress(addressStr)
    }

    //DBのレコード更新
    fun removeDefaultAddress(addressStr: String) = viewModelScope.launch(Dispatchers.IO) {
        var defaultAddressModel = DefaultAddressModel(
            address = addressStr
        )
        repository.deleteDefaultAddress(defaultAddressModel)
    }

    fun defaultAddressDelete(addressStr: String) {
        removeDefaultAddress(addressStr)
    }

}