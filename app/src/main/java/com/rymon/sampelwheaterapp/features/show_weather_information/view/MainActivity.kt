package com.rymon.sampelwheaterapp.features.show_weather_information.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rymon.sampelwheaterapp.features.show_weather_information.viewmodel.WeatherInfoViewModelFactory
import com.rymon.sampelwheaterapp.R
import com.rymon.sampelwheaterapp.database.WeatherInfoTableModel
import com.rymon.sampelwheaterapp.features.show_weather_information.model.data_class.WeatherData
import com.rymon.sampelwheaterapp.features.show_weather_information.viewmodel.WeatherInfoViewModel
import com.rymon.sampelwheaterapp.utils.convertToListOfCityName
import com.rymon.sampelwheaterapp.utils.showAddOrUpdateCityDialog
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_input_part.*
import kotlinx.android.synthetic.main.layout_sunrise_sunset.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var factory: WeatherInfoViewModelFactory
    private lateinit var viewModel: WeatherInfoViewModel
    private var cityList: MutableList<WeatherInfoTableModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize ViewModel
        viewModel = ViewModelProvider(this, factory).get(WeatherInfoViewModel::class.java)
        // set LiveData and View click listeners before the call for data fetching
        setLiveDataListeners()
        setViewClickListener()

    }

    private fun setViewClickListener() {
        // View Weather button click listener
        btn_view_weather.setOnClickListener {
            val selectedCityLat = cityList[spinner.selectedItemPosition].lat
            val selectedCityLon = cityList[spinner.selectedItemPosition].lon
            val selectedCityName = cityList[spinner.selectedItemPosition].cityName

            viewModel.getWeatherInfoByLatLon(selectedCityName,selectedCityLat,selectedCityLon)// fetch weather info

        }
        btn_add_city.setOnClickListener {

            this.showAddOrUpdateCityDialog{
                    cityName : String,
                    lat : String,
                    lon : String ->

                val weatherInfoTableModel = WeatherInfoTableModel(cityName,
                    lat = lat.toDouble(),
                    lon = lon.toDouble())
                viewModel.addCityInfo(weatherInfoTableModel)
            }
        }

        btn_update_city.setOnClickListener {
            val selectedCityLat = cityList[spinner.selectedItemPosition].lat
            val selectedCityLon = cityList[spinner.selectedItemPosition].lon
            val selectedCityName = cityList[spinner.selectedItemPosition].cityName


            this.showAddOrUpdateCityDialog(true,selectedCityName,selectedCityLat,selectedCityLon){
                    cityName : String,
                    lat : String,
                    lon : String ->

                val weatherInfoTableModel = WeatherInfoTableModel(cityName,
                    lat=lat.toDouble(),
                    lon = lon.toDouble())
                viewModel.updateCityInfo(weatherInfoTableModel)
            }
        }
    }

    private fun setLiveDataListeners() {



        viewModel.readAllCityData.observe(this, { cities -> setCityListSpinner2(cities) })

        /**
         * I know it's not good to make separate LiveData both for Success and Failure, but for higher
         * speed I did it. We can handle all of our errors from our Activity or Fragment
         * Base classes. Another way is: using a Generic wrapper class where you can set the success
         * or failure status for any types of data model.
         */
        viewModel.cityListFailureLiveData.observe(this, { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })


        viewModel.progressBarLiveData.observe(this, { isShowLoader ->
            if (isShowLoader)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })

        /**
         * This method will be triggered when ViewModel successfully receive WeatherData from our
         * data source (I mean Model).
         */
        viewModel.weatherInfoLiveData.observe(this, { responseInfo ->
            setWeatherInfo(responseInfo.weather)
            viewModel.updateCityInfo(responseInfo)
        })


        viewModel.weatherInfoFailureLiveData.observe(this, { errorMessage ->
            output_group.visibility = View.GONE
            tv_error_message.visibility = View.VISIBLE
            viewModel. readSelectedCityInfo("selectedCityName").observe(this, Observer {
                /**
                 * This method will be triggered when ViewModel successfully receive WeatherData from our
                 * data source (I mean Model).
                 */
                /**
                 * This method will be triggered when ViewModel successfully receive WeatherData from our
                 * data source (I mean Model).
                 */
                tv_error_message.text = "errorMessage ${it.cityName} and ${it.weather.dateTime}"
                // TODO: 3/26/2022 show data

            })

        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedCityName = cityList[spinner.selectedItemPosition].cityName
//                preference.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                // sometimes you need nothing here
            }
        }
    }

    private fun setCityListSpinner2(cities: MutableList<WeatherInfoTableModel>) {
        this.cityList = cities

        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            this.cityList.convertToListOfCityName()
        )

        spinner.adapter = arrayAdapter

    }



    private fun setWeatherInfo(weatherData: WeatherData) {
        output_group.visibility = View.VISIBLE
        tv_error_message.visibility = View.GONE

        tv_date_time?.text = weatherData.dateTime
        tv_temperature?.text = weatherData.temperature
        tv_city_country?.text = weatherData.cityAndCountry
//        Glide.with(this).load(weatherData.weatherConditionIconUrl).into(iv_weather_condition)
        tv_weather_condition?.text = weatherData.weatherConditionIconDescription

        tv_humidity_value?.text = weatherData.humidity
        tv_pressure_value?.text = weatherData.pressure
        tv_visibility_value?.text = weatherData.visibility

        tv_sunrise_time?.text = weatherData.sunrise
        tv_sunset_time?.text = weatherData.sunset
    }

}