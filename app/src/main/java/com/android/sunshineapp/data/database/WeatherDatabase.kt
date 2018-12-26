package com.android.sunshineapp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [WeatherEntry::class],version = 1)
abstract class WeatherDatabase :RoomDatabase(){
    abstract fun weatherDao():WeatherDao

    //singleton
    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstanceDB(context: Context): WeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                   WeatherDatabase::class.java, "weather").build()
              INSTANCE = instance
                return instance
            }
        }

    }
}