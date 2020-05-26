package com.hblockth.dapp.viewmodels

import android.app.Application
//import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
//import androidx.lifecycle.viewModelScope
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.repositories.AddressRepository
import com.hblockth.dapp.room.db.AddressManageDatabase
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
        repository = AddressRepository(AddressManageDatabase.getMessageDao())
        messages = repository.findAll()
    }

    fun loopingInsert() = viewModelScope.launch(Dispatchers.IO) {
        while (isInserting) {
            val number = (messages.value?.size ?: 0) + 1L
            val message = Message(
                username = "Username $number",
                message = generateRandomString(number),
                time = Date()
            )
            repository.insert(message)
            delay(400)
        }
    }

    fun toggleInsert() {
        isInserting = !isInserting
        buttonText.set(
            if (isInserting)
                "Stop"
            else
                "Start"
        )
        if (isInserting)
            loopingInsert()
    }
}