package com.mostafaimani.weatherappmvvmdagger.features.weather_info_show.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import com.mostafaimani.weatherappmvvmdagger.common.RequestCompleteListener
import com.mostafaimani.weatherappmvvmdagger.database.WeatherDao
import com.mostafaimani.weatherappmvvmdagger.database.WeatherInfoTableModel
import com.mostafaimani.weatherappmvvmdagger.features.weather_info_show.model.data_class.City
import com.mostafaimani.weatherappmvvmdagger.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.mostafaimani.weatherappmvvmdagger.network.ApiInterface
import com.rymon.sampelwheaterapp.features.show_weather_information.model.WeatherInfoShowModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class WeatherInfoShowModelImpl @Inject constructor(
    private val context: Context,
    private val apiInterface: ApiInterface,
    private val weatherDao: WeatherDao
) : WeatherInfoShowModel {


    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList) //let presenter know the city list

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(requireNotNull(e.localizedMessage)) //let presenter know about failure
        }
    }

    override fun getWeatherInfoByCityId(
        cityId: Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {

        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfoByCityId(cityId)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(requireNotNull(response.body())) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(requireNotNull(t.localizedMessage)) //let presenter know about failure
            }
        })
    }

    override fun getWeatherInfoByLatLon(
        lat: Double,
        lon: Double,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {

        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfoByLatLon(lat, lon)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(requireNotNull(response.body())) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(requireNotNull(t.localizedMessage)) //let presenter know about failure
            }
        })
    }

    override val readAllCityData: LiveData<MutableList<WeatherInfoTableModel>>
        get() = weatherDao.getAllCityInfo()


    override  fun readSelectedCityData(selectedCityName: String): LiveData<WeatherInfoTableModel> {
       return weatherDao.getCityInfo(selectedCityName)
    }

    override suspend fun addCityInfo(weatherInfoResponse: WeatherInfoTableModel) {
        weatherDao.addCity(weatherInfoResponse)
    }

    override suspend fun updateCityInfo(weatherInfoTableModel: WeatherInfoTableModel) {
        weatherDao.updateCity(weatherInfoTableModel)
    }

}