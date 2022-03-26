package com.rymon.sampelwheaterapp.features.show_weather_information.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rymon.sampelwheaterapp.features.show_weather_information.model.WeatherInfoShowModel
import javax.inject.Inject

class WeatherInfoViewModelFactory @Inject constructor(private val arg: WeatherInfoShowModel,
                                                      private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(WeatherInfoShowModel::class.java,Application::class.java)
            .newInstance(arg,application)
    }
}