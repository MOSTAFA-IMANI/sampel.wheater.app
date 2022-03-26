package com.rymon.sampelwheaterapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.WeatherData

@Entity(tableName = "WeatherInfoTableModel")
data class WeatherInfoTableModel(
    @PrimaryKey(autoGenerate = false)
    val cityName: String,
    val lat : Double,
    val lon : Double,
    @Embedded
    val weather: WeatherData = WeatherData()

)