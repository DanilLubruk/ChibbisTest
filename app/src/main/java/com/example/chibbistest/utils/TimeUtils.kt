package com.example.chibbistest.utils

import androidx.core.os.LocaleListCompat
import com.example.chibbistest.ChibbisTestApp
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun dateToLocaleStr(date: Date): String {
        try {
            val list = LocaleListCompat.getDefault()
            val current = list[0]
            return DateFormat.getDateInstance(DateFormat.DEFAULT, current).format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val dateFormat = android.text.format.DateFormat.getDateFormat(
            ChibbisTestApp.instance
        )
        return dateFormat.format(date)
    }

    fun dateTo24HoursTime(date: Date): String =
        get24HoursDateFormat().format(date)


    private fun get24HoursDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("HH:mm", Locale.UK)
    }
}