package com.android.sunshineapp.data.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.FileDescriptor
import java.util.*

@Entity(tableName = "weather")
data class WeatherEntry(
    @PrimaryKey
    @ColumnInfo(name = "date")val date: Long,
    @ColumnInfo(name = "min") val min: Double,
    @ColumnInfo(name = "max") val max: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "weather_icon")val weatherIcon:String
)