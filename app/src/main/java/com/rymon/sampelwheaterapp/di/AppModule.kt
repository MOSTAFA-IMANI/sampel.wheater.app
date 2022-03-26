package com.rymon.sampelwheaterapp.di

import android.app.Application
import android.content.Context
import com.rymon.sampelwheaterapp.features.show_weather_information.view.MainActivity
import com.rymon.sampelwheaterapp.network.ApiInterface
import com.rymon.sampelwheaterapp.network.RetrofitClient

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideApiService() : ApiInterface {
            return RetrofitClient.client.create(ApiInterface::class.java)
        }



    }

}