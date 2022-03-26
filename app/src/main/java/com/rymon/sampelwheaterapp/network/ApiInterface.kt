package com.rymon.sampelwheaterapp.network

import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfoByCityId(@Query("id") cityId: Int): Call<WeatherInfoResponse>

    @GET("weather")
    fun callApiForWeatherInfoByLatLon(@Query("lat") lat: Double,
                                      @Query("lon") lan: Double): Call<WeatherInfoResponse>
}