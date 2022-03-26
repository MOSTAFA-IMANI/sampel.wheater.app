package com.rymon.sampelwheaterapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mostafaimani.weatherappmvvmdagger.features.weather_info_show.model.WeatherInfoShowModelImpl
import com.mostafaimani.weatherappmvvmdagger.features.weather_info_show.viewmodel.WeatherInfoViewModelFactory
import com.rymon.sampelwheaterapp.features.show_weather_information.model.WeatherInfoShowModel
import com.rymon.sampelwheaterapp.features.show_weather_information.viewmodel.WeatherInfoViewModel


import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun bindMainViewModel(viewModel: WeatherInfoViewModel): ViewModel

    @Binds
    abstract fun bindModel(weatherInfoShowModelImpl: WeatherInfoShowModelImpl): WeatherInfoShowModel

    @Binds
    abstract fun bindWeatherInfoViewModelFactory(factory: WeatherInfoViewModelFactory): ViewModelProvider.Factory
}