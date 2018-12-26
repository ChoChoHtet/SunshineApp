package com.android.sunshineapp.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import java.util.*


@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getAllWord(): LiveData<List<WeatherEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWord(word: WeatherEntry)

    @Query("DELETE FROM weather")
    fun deleteAll()

    @Query("SELECT COUNT() FROM weather")
    fun countAllFutureWeather():Int
}