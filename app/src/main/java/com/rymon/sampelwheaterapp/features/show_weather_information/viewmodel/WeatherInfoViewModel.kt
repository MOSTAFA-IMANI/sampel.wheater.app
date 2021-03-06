package com.rymon.sampelwheaterapp.features.show_weather_information.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mostafaimani.weatherappmvvmdagger.common.RequestCompleteListener
import com.rymon.sampelwheaterapp.database.WeatherInfoTableModel
import com.rymon.sampelwheaterapp.utils.kelvinToCelsius
import com.rymon.sampelwheaterapp.utils.unixTimestampToDateTimeString
import com.rymon.sampelwheaterapp.utils.unixTimestampToTimeString
import com.rymon.sampelwheaterapp.features.show_weather_information.model.WeatherInfoShowModel
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.City
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.WeatherData
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.WeatherInfoResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherInfoViewModel @Inject constructor(
    var model: WeatherInfoShowModel,
    var application: Application
    ) : ViewModel() {
    val readAllCityData : LiveData<MutableList<WeatherInfoTableModel>> = model.readAllCityData

    /**
     *  We can use a wrapper data class to implement success and failure both using a single LiveData.
     *  Another good approach may be handle errors in BaseActivity.
     *  just for more speed i rather use this one
     */
    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()
    val weatherInfoLiveData = MutableLiveData<WeatherInfoTableModel>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    init {
//        val weatherDao = WeatherDataBase.getDatabase(application).WeatherDao()

    }

    fun readSelectedCityInfo(selectedCityName:String): LiveData<WeatherInfoTableModel>{

        return model.readSelectedCityData(selectedCityName)

    }

    fun addCityInfo(weatherInfoTableModel: WeatherInfoTableModel){

        CoroutineScope(Dispatchers.IO).launch {
            model.addCityInfo(weatherInfoTableModel)
        }
    }
    fun updateCityInfo(weatherInfoTableModel: WeatherInfoTableModel){

        CoroutineScope(Dispatchers.IO).launch {
            model.updateCityInfo(weatherInfoTableModel)
        }
    }

    /**We can inject the instance of Model in Constructor using dependency injection.
     * I know it's not a good approach.
     */
    fun getCityList() {

        model.getCityList(object :
            RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                cityListLiveData.postValue(data) // PUSH data to LiveData object
            }

            override fun onRequestFailed(errorMessage: String) {
                cityListFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
            }
        })
    }

    /**We can inject the instance of Model in Constructor using dependency injection.
     * I know it's not a good approach.
     */
    fun getWeatherInfoByLatLon(selectedCityName :String,lat: Double,lon: Double) {

        progressBarLiveData.postValue(true) // PUSH data to LiveData object to show progress bar

        model.getWeatherInfoByLatLon(lat,lon, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                // business logic and data manipulation tasks should be done here
                val weatherData = WeatherData(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )
                val weatherInfoTableModel=WeatherInfoTableModel(selectedCityName,lat,lon,weatherData)

                progressBarLiveData.postValue(false) // PUSH data to LiveData object to hide progress bar

                // After applying business logic and data manipulation, we push data to show on UI
                weatherInfoLiveData.postValue(weatherInfoTableModel) // PUSH data to LiveData object
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false) // hide progress bar
                weatherInfoFailureLiveData.postValue(errorMessage) // PUSH error message to LiveData object
            }
        })
    }
}