package com.hblockth.dapp

import androidx.room.Room
import com.hblockth.dapp.room.db.AppDatabase

//https://qiita.com/sudachi808/items/d629e44fe8c1b9a26bb8
class DApplication : android.app.Application() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            objectOf<AppDatabase>(), "database-dapp"
        ).build()
    }
}

internal inline fun <reified T : Any> objectOf() = T::class.java