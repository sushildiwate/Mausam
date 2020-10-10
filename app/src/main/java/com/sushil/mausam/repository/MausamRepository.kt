package com.sushil.mausam.repository

import com.sushil.mausam.model.WeatherModel
import com.sushil.mausam.network.MausamApi
import io.reactivex.Observable

open class MausamRepository(private val mausamApi: MausamApi) {

    fun getWeatherFromAPI(
        lat: Double,
        lon: Double,
        api_key: String,
        unit:String
    ): Observable<WeatherModel> {
        return mausamApi.getWeather(lat, lon, api_key,unit)
    }

}