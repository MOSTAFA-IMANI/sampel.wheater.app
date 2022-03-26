package com.mostafaimani.weatherappmvvmdagger.features.weather_info_show.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mostafaimani.weatherappmvvmdagger.features.weather_info_show.model.WeatherInfoShowModel
import javax.inject.Inject

class WeatherInfoViewModelFactory @Inject constructor(private val arg: WeatherInfoShowModel,
                                                      private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(WeatherInfoShowModel::class.java,Application::class.java)
            .newInstance(arg,application)
    }
}