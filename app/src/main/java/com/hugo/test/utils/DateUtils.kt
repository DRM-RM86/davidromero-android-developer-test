package com.hugo.test.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {


    const val SECONDS_IN_DAY: Long = 86400

    /**
     * Formats the given date into the specified format
     */
    @JvmStatic
    fun format(date: Date?, format: String): String {
        if(date == null)
            return ""
        return SimpleDateFormat(format, Locale.US).format(date)
    }

    @JvmStatic
    fun format(date: Date, format: String, timeZone: TimeZone): String {
        val sdf = SimpleDateFormat(format, Locale.US)
        sdf.timeZone = timeZone
        return sdf.format(date)
    }

    @JvmStatic
    fun formatDate(date: Date?): String {
        var date = date
        if (date == null)
            date = Date()
        return format(
            date,
            "dd/MM/yyyy"
        )
    }


     fun getFormatDate(date: Date?): String {
        return if (date == null)
            "-/-/-"
        else
            format(date, "dd-MM-yyyy HH:mm:ss")
    }


    fun twoDatesBetweenTime(oldtime: String?, type:Int): String? {

        var day = 0
        var hh = 0
        var mm = 0
        try {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            val oldDate = dateFormat.parse(oldtime)
            val cDate = Date()
            val timeDiff = cDate.time - oldDate.time
            day = TimeUnit.MILLISECONDS.toDays(timeDiff).toInt()
            hh = ((TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day.toLong())).toInt())
            mm = ((TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff))).toInt())

            } catch (e: ParseException) {
            e.printStackTrace()
        }

        val minutes = (day * 1440) + (hh*60) + mm

        when (type){
            0->{
                return minutes.toString()
            }
            1->{
                return when {
                    day == 0 -> {
                        "$hh Hrs $mm min"
                    }
                    hh == 0 -> {
                        "$mm min"
                    }
                    else -> {
                        "$day Dias $hh horas $mm min"
                    }
                }
            }

        }
       return ""
    }


}