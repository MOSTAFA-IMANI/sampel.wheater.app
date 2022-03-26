package com.mostafaimani.weatherappmvvmdagger.di

import android.app.Application
import com.rymon.sampelwheaterapp.common.App
import com.rymon.sampelwheaterapp.features.show_weather_information.viewmodel.WeatherInfoViewModel
import com.rymon.sampelwheaterapp.di.AppModule

import dagger.BindsInstance
import dagger.Component

import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class]
)
interface ApplicationComponent : AndroidInjector<App> {

    fun inject(weatherInfoViewModel: WeatherInfoViewModel)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}