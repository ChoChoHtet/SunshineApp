package com.android.sunshineapp.model

import com.google.gson.annotations.SerializedName

data class MainWeatherEntry(
    @SerializedName("list")
    var weatherList: List<WeatherList>
)
data class WeatherList(
    @SerializedName("dt")
    var dt: Long,

    @SerializedName("main")
    var temp: Temp,
    @SerializedName("pressure")
    var pressure: Double,

    @SerializedName("humidity")
    var humidity: Double,

    @SerializedName("weather")
    var weather: List<Weather>,

    @SerializedName("speed")
    var speed: Double,

    @SerializedName("deg")
    var deg: Int
)

data class Temp(
    @SerializedName("day")
    var day: Double,

    @SerializedName("temp_min")
    var min: Double,

    @SerializedName("temp_max")
    var max: Double
)

data class Weather(
    @SerializedName("id")
    var id: Int,

    @SerializedName("description")
    var description: String,

    @SerializedName("icon")
    var icon: String
)