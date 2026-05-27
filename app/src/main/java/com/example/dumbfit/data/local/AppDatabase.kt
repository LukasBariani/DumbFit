package com.example.dumbfit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dumbfit.data.local.dao.TreinoDao
import com.example.dumbfit.data.local.dao.TecnicaDao
import com.example.dumbfit.data.local.entity.TreinoEntity
import com.example.dumbfit.data.local.entity.TecnicaEntity

@Database(
    entities = [TreinoEntity::class, TecnicaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun treinoDao(): TreinoDao
    abstract fun tecnicaDao(): TecnicaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dumbfit_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}