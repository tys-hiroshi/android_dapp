package com.hblockth.dapp.features.addressmng

//https://github.com/arifnadeem7/mvvmcoroutinesandflow/blob/15f031944b3d8a6b0825dea063aaa45ac1f9e3f2/app/src/main/java/com/arif/kotlincoroutinesplusflow/features/weather/WeatherRepository.kt
//import com.hblockth.dapp.base.Error
//import com.hblockth.dapp.base.Success
//import com.hblockth.dapp.custom.aliases.WeatherResult
//import com.hblockth.dapp.custom.errors.ErrorHandler
//import com.hblockth.dapp.custom.errors.NoDataException
//import com.hblockth.dapp.custom.errors.NoResponseException
//import com.hblockth.dapp.entitymappers.weather.WeatherMapper
//import com.hblockth.dapp.extensions.applyCommonSideEffects
//import com.hblockth.dapp.features.home.di.HomeScope
//import com.hblockth.dapp.network.api.OpenWeatherApi
//import com.hblockth.dapp.network.response.ErrorResponse
//import com.hblockth.dapp.room.dao.utils.StringKeyValueDao
import com.github.kittinunf.result.Result
import com.hblockth.dapp.features.home.di.HomeScope
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.utils.Utils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HomeScope
class AddressManageRepository @Inject constructor(
    //private val openWeatherApi: OpenWeatherApi,
    private val addressManageDao: AddressManageDao,
//    private val stringKeyValueDao: StringKeyValueDao
) {

    private val weatherCacheThresholdMillis = 3600000L //1 hour//

    fun getWeather(cityName: String) = flow {
        stringKeyValueDao.get(Utils.LAST_WEATHER_API_CALL_TIMESTAMP)
            ?.takeIf { !Utils.shouldCallApi(it.value, weatherCacheThresholdMillis) }
            ?.let { emit(getDataOrError(NoDataException())) }
            ?: emit(getWeatherFromAPI(cityName))
    }
        .applyCommonSideEffects()
        .catch {
            emit(getDataOrError(it))
        }

    /**
     * Another way...
     *
     * Use this pattern when your data can change from different places.
     * This method calls the API and then saves it's response to the database.
     * Caller should also use the function getWeatherDBFlow() to listen for changes and
     * update the UI accordingly.
     *
     * Expected usage
     *
     * Expose an immutable LiveData from your ViewModel to observe DB changes in your View.
     *
     * In your ViewModel call this inside init{} block
     *
     *      viewModelScope.launch {
     *      weatherRepository.getWeatherDBFlow()
     *      .collect { mutableWeatherData.value = it }
     *      }
     *
     *  Then call this function callWeatherApi(cityName) from your View's lifecycleScope
     *
     */

    private suspend fun getDataOrError(throwable: Throwable) =
        addressManageDao.getAll()
            ?.let { dbValue -> Result.Success(dbValue) }
            ?: Error(throwable)

    //Observe DB changes
    fun getAll() = addressManageDao.getAll()
}