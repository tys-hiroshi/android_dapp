package com.hblockth.dapp.di.modules

import android.content.Context
import androidx.room.Room
import com.hblockth.dapp.room.db.AddressManageDatabase
import com.hblockth.dapp.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DbModule {

    @Provides
    @Singleton
    fun provideWeatherDB(context: Context): AddressManageDatabase {
        return Room.databaseBuilder(context, AddressManageDatabase::class.java, Utils.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providerAddressManageDao(addressManageDatabase: AddressManageDatabase) =
        addressManageDatabase.addressManageDao()

}