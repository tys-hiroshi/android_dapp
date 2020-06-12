package com.hblockth.dapp.viewmodels

import android.app.Application
//import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.repositories.AddressRepository
import com.hblockth.dapp.room.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class AddressListViewModel (application: Application) : AndroidViewModel(application) {

    val addresses: LiveData<List<AddressModel>>
    val repository: AddressRepository

    init {
        repository = AddressRepository(AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao())
        addresses = repository.findAllForLiveData()
    }

    //DBÇ÷ÉåÉRÅ[Éhí«â¡
    fun deleteAddress(addressStr: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAddress(addressStr)
    }

    fun AddressDelete(addressStr: String) {
        deleteAddress(addressStr)
    }
}