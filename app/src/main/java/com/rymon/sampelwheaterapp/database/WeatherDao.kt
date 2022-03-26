package com.rymon.sampelwheaterapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDao {

    @Query("Select * from WeatherInfoTableModel")
    fun  getAllCityInfo(): LiveData<MutableList<WeatherInfoTableModel>>

    @Query("Select * from WeatherInfoTableModel where cityName = :selectedCityName")
    fun  getCityInfo(selectedCityName : String): LiveData<WeatherInfoTableModel>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addCity(cityInfo:WeatherInfoTableModel)

    @Update()
    suspend fun updateCity(cityInfo:WeatherInfoTableModel)



}