package com.rymon.sampelwheaterapp.utils

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import com.rymon.sampelwheaterapp.R
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.City
import java.text.SimpleDateFormat
import java.util.*

fun Int.unixTimestampToDateTimeString(): String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("dd MMM, yyyy - hh:mm a", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault() // user's default time zone
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}

fun Int.unixTimestampToTimeString(): String {

    try {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000.toLong()

        val outputDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        outputDateFormat.timeZone = TimeZone.getDefault()
        return outputDateFormat.format(calendar.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return this.toString()
}

// TODO: 3/26/2022 add

/**
 * The temperature T in degrees Celsius (°C) is equal to the temperature T in Kelvin (K) minus 273.15:
 * T(°C) = T(K) - 273.15
 *
 * Example
 * Convert 300 Kelvin to degrees Celsius:
 * T(°C) = 300K - 273.15 = 26.85 °C
 */
fun Double.kelvinToCelsius(): Int {

    return (this - 273.15).toInt()
}

// TODO: 3/26/2022 add