package com.android.sunshineapp.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import com.android.sunshineapp.AppExecutor
import com.android.sunshineapp.data.database.WeatherDao
import com.android.sunshineapp.data.database.WeatherEntry
import com.android.sunshineapp.data.network.WeatherNetworkDataSource
import com.android.sunshineapp.model.WeatherList

class SunshineRepository(
    private val weatherDao: WeatherDao,
    private val networkData: WeatherNetworkDataSource,
    private val appExecutor: AppExecutor
) {
    /**
     * if live data change, update on db
     */
    private var initialized = false

    init {

        val allWeather = networkData.getAllWeather()
        allWeather.observeForever {
            appExecutor.diskIO().execute {
                Log.e("Loop Again","Again")
                it!!.forEach { weather ->
                    weatherDao.addWord(
                        WeatherEntry(
                            weather.dt,
                            weather.temp.min,
                            weather.temp.max,
                            weather.weather[0].description,
                            weather.weather[0].icon
                        )
                    )
                }
                val count = weatherDao.countAllFutureWeather()
                Log.e("Count", count.toString())
            }
        }
    }

    /**
     * get all words from db
     */
    fun getAllWords(): LiveData<List<WeatherEntry>> {
        initializedData()
        return weatherDao.getAllWord()
    }

    /**
     * get all words from db
     */
    fun getCurrentData(): LiveData<List<WeatherList>> {
        initializedData()
        networkData.fetchWeatherData()
        return networkData.getAllWeather()
    }

    /**
     * initialize data
     */
    @Synchronized
    fun initializedData() {
        if (initialized) return
        initialized = true
        appExecutor.diskIO().execute {
            if (isFetchNeeded()) {
                Log.e("Data", "fetched data again")
                networkData.fetchWeatherData()
            }
        }
        Log.e("Initialized", initialized.toString())
    }

    private fun isFetchNeeded(): Boolean {
        val count = weatherDao.countAllFutureWeather()
        return (count < WeatherNetworkDataSource.NUMBER)
    }

    /**
     * store word
     */
    fun addWord(word: WeatherEntry) {
        appExecutor.diskIO().execute {
            weatherDao.addWord(word)
        }
    }

    //singleton
    companion object {
        private var sInstance: SunshineRepository? = null
        fun getInstanceRepository(
            weatherDao: WeatherDao,
            networkData: WeatherNetworkDataSource,
            appExecutor: AppExecutor
        ): SunshineRepository {
            if (sInstance != null) return sInstance!!
            synchronized(this) {
                sInstance = SunshineRepository(weatherDao, networkData, appExecutor)
                return sInstance!!
            }
        }
    }


}