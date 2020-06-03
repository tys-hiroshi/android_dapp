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

class AddAddressViewModel (application: Application) : AndroidViewModel(application) {
    val repository: AddressRepository

    //val buttonText = ObservableField("Start")
    var isInserting = false

    init {
        repository = AddressRepository(AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao())
    }

    //DBへレコード追加
    fun addAddress(addressStr: String, privateKeyWifStr: String, mnemonicStr: String) = viewModelScope.launch(Dispatchers.IO) {
        var addressModel = AddressModel(
            address = addressStr,
            privateKeyWif = privateKeyWifStr,
            mnemonic = mnemonicStr,
            createdAt = Date()
        )
        repository.insert(addressModel)
    }

    fun newAddressInsert(addressStr: String, privateKeyWifStr: String, mnemonicStr: String) {
        addAddress(addressStr, privateKeyWifStr, mnemonicStr)
    }
}