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

    fun addAddress(addressStr: String) = viewModelScope.launch(Dispatchers.IO) {
        var addressModel = AddressModel(
            address = addressStr
        )
        repository.insert(addressModel)
    }

    fun toggleInsert(addressStr: String) {
        isInserting = !isInserting
//        buttonText.set(
//            if (isInserting)
//                "Stop"
//            else
//                "Start"
//        )
        if (isInserting)
            addAddress(addressStr)
    }
}