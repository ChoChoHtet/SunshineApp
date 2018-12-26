package com.android.sunshineapp.data.retrofit

import com.android.sunshineapp.model.MainWeatherEntry
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface WeatherService {
    @Headers("x-api-key: 4e5b71286ebaf25153411d475463fcd7")
    @GET("data/2.5/forecast")
    fun getWeatherData(@Query("lat")latitude:Double,@Query("lon") longitude:Double): Single<MainWeatherEntry>
}