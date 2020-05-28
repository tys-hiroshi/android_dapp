package com.hblockth.dapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.hblockth.dapp.repositories.AddressRepository
import com.hblockth.dapp.room.db.AppDatabase
import com.hblockth.dapp.room.models.addressmng.AddressModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddressViewModelFactory(private val mApplication: Application, private val mParam: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddressViewModel(mApplication, mParam) as T
    }

}

class AddressViewModel(application: Application, mParam: String) : AndroidViewModel(application) {
    val addressModel: LiveData<AddressModel>
    val repository: AddressRepository

    init {
        repository = AddressRepository(
            AppDatabase.getDatabase(application, viewModelScope).getAddressManageDao()
        )
        addressModel = repository.findByAddressForLiveData(mParam)
    }

    //DBへレコード追加
//    fun getAddressSecretInfoForCoroutine(addressStr: String) = viewModelScope.launch(
//        Dispatchers.IO) {
//        repository.findByAddress(addressStr)
//    }
//https://github.com/rahul-evontech/RetrofitCRUD/blob/20442464b9c1adccc94256772785dcffa4c9b0eb/app/src/main/java/com/smartherd/retrofitjosn/viewModel/MainViewModel.kt
//    fun getAddressSecretInfoForCoroutine(addressStr: String): LiveData<AddressModel>{
//        viewModelScope.launch{
//            repository.findByAddress(addressStr)
//        }
//
//        return addressModel  //addressModel
//    }

//    fun getAddressSecretInfoForCoroutine(addressStr: String) = viewModelScope.launch(Dispatchers.IO){
//        val response1 = repository.findAllForLiveData()
//        val response = repository.findByAddressForLiveData(addressStr)
//        println("response.value")
//        println(response.value)
//        var aaaa = withContext(Dispatchers.Default) {
//            val response2 = repository.findByAddressForLiveData(addressStr)
//            println(response2.value)
//        }
////        response1.observe(this, Observer { address ->
////            println("address")
////            println(address)
////        })
//        println(aaaa)
//        //addressModel.value = response.value
//    }
//
//    fun getAddressSecretInfo(addressStr: String):Job {
//        return getAddressSecretInfoForCoroutine(addressStr)
//    }
}