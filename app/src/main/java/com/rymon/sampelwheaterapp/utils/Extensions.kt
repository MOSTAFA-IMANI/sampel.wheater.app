package com.rymon.sampelwheaterapp.utils

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import com.rymon.sampelwheaterapp.R
import com.rymon.sampelwheaterapp.database.WeatherInfoTableModel
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


fun MutableList<City>.convertToListOfCityInfo(): MutableList<String> {

    val cityNameList: MutableList<String> = mutableListOf()

    for (city in this) {
        val weatherInfoTableModel: WeatherInfoTableModel
        cityNameList.add(city.name)
    }
    // TODO: 3/17/2022 remove or make it better

    return cityNameList
}


fun MutableList<WeatherInfoTableModel>.convertToListOfCityName(): MutableList<String> {

    val cityNameList: MutableList<String> = mutableListOf()

    for (cityInfo in this) {
        val cityName = cityInfo.cityName
        val lat = cityInfo.lat
        val lon = cityInfo.lon
        cityNameList.add("$cityName  ( $lat  ,  $lon ) ")
    }

    return cityNameList
}

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

fun Context.showAddOrUpdateCityDialog(
    isUpdate: Boolean? = false,
    cityName: String? = null,
    coordinatorLat: Double? = null,
    coordinatorLon: Double? = null,
    addButtonFunction: ((cityName: String, lat: String, lon: String) -> Unit)? = null
) {
    val dialog = Dialog(this, R.style.Widget_AppCompat_ButtonBar_AlertDialog)
//    dialog.window?.requestFeature(Window.FEATURE_NO_TITLE) // if you have blue line on top of your dialog, you need use this code
//    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    dialog.setCancelable(false)
    dialog.setContentView(R.layout.layout_dialog_add_city)
    val dialogCityName = dialog.findViewById(R.id.city_name_input) as EditText
    val dialogLatInput = dialog.findViewById(R.id.lat_input) as EditText
    val dialogLonInput = dialog.findViewById(R.id.lon_input) as EditText
    val dialogAddButton = dialog.findViewById(R.id.btn_dialog_add) as Button

    isUpdate?.let {
        if (isUpdate) {
            dialogAddButton.text = getString(R.string.update_city)
            dialogCityName.isEnabled = false
        }
    }
    cityName?.let { dialogCityName.setText(it) }
    coordinatorLat?.let { dialogLatInput.setText(it.toString()) }
    coordinatorLon?.let { dialogLonInput.setText(it.toString()) }
    dialogAddButton.setOnClickListener {
        if (dialogCityName.text.isEmpty()) {
            dialogCityName.error = getString(R.string.empty_input_error)
        } else {
            addButtonFunction?.invoke(
                dialogCityName.text.toString(),
                dialogLatInput.text.toString(),
                dialogLonInput.text.toString()
            )
            dialog.dismiss()
        }
    }
    dialog.show()
}
