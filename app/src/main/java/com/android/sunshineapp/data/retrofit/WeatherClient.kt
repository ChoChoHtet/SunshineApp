package com.android.sunshineapp.data.retrofit

import android.util.Log
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient {
    //  private const val BASE_URL="https://andfun-weather.udacity.com/"
    private const val BASE_URL = "https://api.openweathermap.org/"
   private var wInstance: Retrofit? = null

    val instance: Retrofit
        get() {
            if (wInstance == null) {
                Log.e("Retrofit", "Created")
                wInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return wInstance!!
        }
}