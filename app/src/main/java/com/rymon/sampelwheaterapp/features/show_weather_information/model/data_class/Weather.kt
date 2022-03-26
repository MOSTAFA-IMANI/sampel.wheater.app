package com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class


import com.google.gson.annotations.SerializedName

data class Weather(
        @SerializedName("id")
        val weatherId: Int = 0,
        @SerializedName("main")
        val main: String = "",
        @SerializedName("description")
        val description: String = "",
        @SerializedName("icon")
        val icon: String = ""
)