package com.hblockth.dapp.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hblockth.dapp.converters.Converters
import com.hblockth.dapp.room.dao.addressmng.AddressManageDao
import com.hblockth.dapp.room.models.addressmng.AddressModel
import com.hblockth.dapp.room.models.addressmng.DefaultAddressModel
import com.hblockth.dapp.room.models.addressmng.UploadTxIdModel
import com.hblockth.dapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [AddressModel::class, DefaultAddressModel::class, UploadTxIdModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAddressManageDao(): AddressManageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                Room.databaseBuilder(context, AppDatabase::class.java, Utils.DATABASE_NAME)
                    .addMigrations(object: Migration(1, 2){
                        override fun migrate(database: SupportSQLiteDatabase) {
                        }
                    })
                    .build();
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            Utils.DATABASE_NAME
                        )
                        .addCallback(AppDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    //populateDatabase(database.getAddressManageDao())
                }
            }
        }

//        fun populateDatabase(dao: AddressManageDao) {
//            if (dao.getAllBlocking().isEmpty()) {
//                val addresses = mutableListOf<AddressModel>()
//                for (i in 1..100) {
//                    val address = AddressModel(
//                        address = "address $i"
//                    )
//                    addresses.add(address)
//                }
//                dao.insert(*addresses.toTypedArray())
//            }
//        }
    }
}