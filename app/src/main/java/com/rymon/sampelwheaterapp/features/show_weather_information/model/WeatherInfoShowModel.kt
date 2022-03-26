package com.rymon.sampelwheaterapp.features.show_weather_information.model

import androidx.lifecycle.LiveData
import com.mostafaimani.weatherappmvvmdagger.common.RequestCompleteListener
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.City
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfoByCityId(
        cityId: Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    )

    fun getWeatherInfoByLatLon(
        lat: Double,
        lon: Double,
        callback: RequestCompleteListener<WeatherInfoResponse>
    )



}