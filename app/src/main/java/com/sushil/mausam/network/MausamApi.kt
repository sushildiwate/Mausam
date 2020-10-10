package com.sushil.mausam.network

import com.sushil.mausam.model.WeatherModel
import com.sushil.mausam.utils.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MausamApi {

    @GET(WEATHER_FORECAST)
    fun getWeather(
        @Query(LATITUDE) lat: Double,
        @Query(LONGITUDE) lon: Double,
        @Query(APP_ID) appid: String,
        @Query(UNITS) unit: String
    ): Observable<WeatherModel>
}