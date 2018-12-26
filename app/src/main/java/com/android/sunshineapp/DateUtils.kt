package com.android.sunshineapp

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {
    /**
     * milli second in a day
     */
    companion object {
        val DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1)
        fun getNormalizedUtcMsForToday(): Long {
            /**
             * the number of milliseconds represent that have elapsed time in a GMT time zone
             */
            val utcNowMillis = System.currentTimeMillis()
            /**
             * device's current time zone from utc time
             */
            val currentTimeZone = TimeZone.getDefault()
            /**
             * returns the number of milliseconds  add to UTC time
             */
            val gmtOffset = currentTimeZone.getOffset(utcNowMillis)
            /**
             *the number of milliseconds in GMT
             */
            val timesSinceEpochTimeMillis = utcNowMillis + gmtOffset
            /**
             * This method simply converts milliseconds to days
             */
            val daySinceEpoch = TimeUnit.MILLISECONDS.toDays(timesSinceEpochTimeMillis)
            /**
             * Finally, we convert back to milliseconds. This time stamp represents today's date at
             * midnight in GMT time. We will need to account for local time zone offsets when
             * extracting this information from the database.
             */
            return TimeUnit.DAYS.toMillis(daySinceEpoch)

        }

        fun getNormalizedUtcDateForToday(): Date {
            return Date(getNormalizedUtcMsForToday())
        }

        /**
         * the number of days in utc time from current data
         * @param utc - a date in millisecond in utc time
         */
        private fun elapseDateSinceEpoch(utcDate: Long): Long {
            return TimeUnit.MILLISECONDS.toDays(utcDate)
        }

        private fun getLocalMidnightFromNormalizedUtcDate(normalizedUtcDate: Long): Long {
            val timeZone = TimeZone.getDefault()
            val gmtOffset = timeZone.getOffset(normalizedUtcDate)
            return normalizedUtcDate - gmtOffset
        }

        fun getFriendlyDateString(context: Context, normalizedUtcMidnight: Long, fullDate: Boolean): String {
            val localDate = getLocalMidnightFromNormalizedUtcDate(normalizedUtcMidnight)
            val daysFromEpochToProvidedDate = elapseDateSinceEpoch(localDate)

            val daysFromEpochToToday = elapseDateSinceEpoch(System.currentTimeMillis())

            if (daysFromEpochToProvidedDate == daysFromEpochToToday || fullDate) {

                val dayName = getDayName(context, localDate)
                val readableDate = getReadableDateString(context, localDate)
                if (daysFromEpochToProvidedDate - daysFromEpochToToday < 2) {
                    val localizedDayName = SimpleDateFormat("EEEE").format(localDate)
                    Log.e("Day",readableDate.replace(localizedDayName, dayName))
                    return readableDate.replace(localizedDayName, dayName)
                } else {
                    return readableDate
                }

            } else if (daysFromEpochToProvidedDate < daysFromEpochToToday + 7) {
                return getDayName(context, localDate)
            } else {
                val flags = (DateUtils.FORMAT_SHOW_DATE
                        or DateUtils.FORMAT_NO_YEAR
                        or DateUtils.FORMAT_ABBREV_ALL
                        or DateUtils.FORMAT_SHOW_WEEKDAY)

                return DateUtils.formatDateTime(context, localDate, flags)
            }
        }

        private fun getReadableDateString(context: Context, timeInMillis: Long): String {
            val flags = (DateUtils.FORMAT_SHOW_DATE
                    or DateUtils.FORMAT_NO_YEAR
                    or DateUtils.FORMAT_SHOW_WEEKDAY)

            return DateUtils.formatDateTime(context, timeInMillis, flags)
        }

        private fun getDayName(context: Context, dateInMillis: Long): String {
            val daysFromEpochToProvidedDate = elapseDateSinceEpoch(dateInMillis)
            val daysFromEpochToToday = elapseDateSinceEpoch(System.currentTimeMillis())

            val daysAfterToday = (daysFromEpochToProvidedDate - daysFromEpochToToday).toInt()

            when (daysAfterToday) {
                0 -> return context.getString(R.string.today)
                1 -> return context.getString(R.string.tomorrow)

                else -> {
                    val dayFormat = SimpleDateFormat("EEE")
                    return dayFormat.format(dateInMillis)
                }
            }
        }
    }
}