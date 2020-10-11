package com.sushil.mausam.ui.weather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushil.mausam.R
import com.sushil.mausam.model.WeatherModel
import com.sushil.mausam.preference.PreferenceProviderMausam
import com.sushil.mausam.ui.weather.adapter.ForecastAdapter
import com.sushil.mausam.utils.*
import com.sushil.mausam.viewmodel.MausamViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherActivity : AppCompatActivity(), ForecastAdapter.ForecastClickListener {
    private val mMausamViewModel: MausamViewModel by viewModel()
    private var mLatitude: Double = DEFAULT_LATITUDE
    private var mLongitude: Double = DEFAULT_LONGITUDE
    private lateinit var adapter: ForecastAdapter
    private lateinit var preference: PreferenceProviderMausam
    private var forecastList: MutableList<WeatherModel.Forecast> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        preference = PreferenceProviderMausam(this)
        getDataFromIntent()
        getWeatherData()
        subscribeObserver()
        adapter = ForecastAdapter(forecastList, this)
        adapter.setOnItemClickListener(this)
        image_view_back_arrow.setOnClickListener { pushToBack(this) }
        imageViewRefresh.setOnClickListener { getWeatherData() }
    }

    private fun getWeatherData() {
        mMausamViewModel.getWeather(
            mLatitude,
            mLongitude,
            WEATHER_API_KEY,
            preference.getUnitType().toLowerCase()
        )
    }

    private fun getDataFromIntent() {
        mLatitude = intent.extras?.get(getString(R.string.latitude)) as Double
        mLongitude = intent.extras?.get(getString(R.string.longitude)) as Double
    }

    private fun subscribeObserver() {
        mMausamViewModel.responseMutableLiveData.observe(this, Observer { weather ->
            textViewCity.text = weather.city.name
            textViewUnitType.text = "Unit type: ${preference.getUnitType()} "
            setWeatherData(weather.getForecast())
            if (forecastList.isNotEmpty())
                forecastList.clear()
            else {
                for (item in weather.list) {
                    forecastList.add(
                        WeatherModel.Forecast(
                            item.clouds,
                            item.deg,
                            item.dt,
                            item.feelsLike,
                            item.humidity,
                            item.pop,
                            item.pressure,
                            item.speed,
                            item.sunrise,
                            item.sunset,
                            item.temp,
                            item.weather
                        )
                    )
                }
                forecastList[0].isSelected = true
                adapter.notifyDataSetChanged()
            }
            recyclerViewForecast.adapter = adapter
            recyclerViewForecast.layoutManager = LinearLayoutManager(this)
        })
        mMausamViewModel.errorMessage.observe(this, Observer { error -> toast(error) })

        //Hide/show the progress bar
        mMausamViewModel.progressBar.observe(this, Observer {
            when (it) {
                0 -> progressBar.visibility = View.VISIBLE

                1 -> progressBar.visibility = View.GONE

                else -> progressBar.visibility = View.GONE
            }

        })
    }

    private fun setWeatherData(forecast: WeatherModel.Forecast) {
        textViewTemperature.text = "Temperature: ${forecast.getMinMaxTemp()} °"
        textViewClimate.text =
            "Climate: ${forecast.getForecastWeather().getWeatherDescription()}"
        textViewHumidity.text = "Humidity: ${forecast.getHumidity()}"
        textViewWindSpeed.text = "Wind Speed: ${forecast.speed}"
        textViewPressure.text = "Pressure: ${forecast.pressure}"
        textViewWindDegree.text = "Wind Degree: ${forecast.deg}"
        val url =
            "http://openweathermap.org/img/wn/${forecast.getForecastWeather().icon}@4x.png"
        imageViewWeatherIcon.glideWith(url, this)
    }

    override fun onItemClick(
        adapterPosition: Int
    ) {
        setWeatherData(forecastList[adapterPosition])
        for (item in forecastList) {
            item.isSelected = false
        }
        forecastList[adapterPosition].isSelected = true
        adapter.notifyDataSetChanged()
    }

}