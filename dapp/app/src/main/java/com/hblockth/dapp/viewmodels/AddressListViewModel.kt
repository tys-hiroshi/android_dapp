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

    //val buttonText = ObservableField("Start")
    var isInserting = false

    init {
        repository = AddressRepository(AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao())
        addresses = repository.findAllForLiveData()
    }

    fun loopingInsert() = viewModelScope.launch(Dispatchers.IO) {
        while (isInserting) {
            val number = (addresses.value?.size ?: 0) + 1L
            val address = AddressModel(
                address = "address $number"
            )
            repository.insert(address)
            delay(1000)
        }
    }

    fun toggleInsert() {
        isInserting = !isInserting
//        buttonText.set(
//            if (isInserting)
//                "Stop"
//            else
//                "Start"
//        )
        if (isInserting)
            loopingInsert()
    }
}