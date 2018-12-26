package com.android.sunshineapp.data.database

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {
    companion object {
        @TypeConverter
        fun toDate(timeStamp:Long?): Date?{
            return if (timeStamp==null) null else Date(timeStamp)
        }
        @TypeConverter
        fun toTimeStamp(date: Date?):Long?{
            return if (date==null) null else date!!.time
        }
    }
}