package com.android.sunshineapp.data.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.android.sunshineapp.AppExecutor
import com.android.sunshineapp.data.retrofit.WeatherClient
import com.android.sunshineapp.data.retrofit.WeatherService
import com.android.sunshineapp.model.WeatherList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherNetworkDataSource(private val appExecutor: AppExecutor) {
    private lateinit var apiData:WeatherService
    private var downloadedData=MutableLiveData<List<WeatherList>>()

    //singleton
    companion object {
        private var sInstance:WeatherNetworkDataSource?=null
        const val NUMBER=14
        fun getInstanceSource(appExecutor: AppExecutor):WeatherNetworkDataSource{
            if (sInstance !=null)
                return sInstance!!

            synchronized(this){
                sInstance=WeatherNetworkDataSource(appExecutor)
                return sInstance!!
            }

        }
    }
    fun getAllWeather():LiveData<List<WeatherList>>{
        return downloadedData
    }
   // fun getListSize():Int=weatherListSize.size

    //fetch data
    fun fetchWeatherData(){
        apiData=WeatherClient.instance.create(WeatherService::class.java)
        appExecutor.networkIO().execute {
            apiData.getWeatherData(16.871311,96.199379)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    Log.e("NetworkSource",it.weatherList.size.toString())
                    downloadedData.postValue(it.weatherList)
                  //  weatherListSize=it.weatherList
                }
                .doOnError {
                    Log.e("NetworkSource",it.localizedMessage)
                }.subscribe()

        }

    }
}