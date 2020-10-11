package com.sushil.mausam.ui.home

import androidx.lifecycle.LiveData
import com.sushil.mausam.MausamApplication
import com.sushil.mausam.database.City
import com.sushil.mausam.database.CityDao


/**
 * Repository for Home screen
 * */
class HomeRepository(private val cityDao: CityDao?) {

    suspend fun deleteCityFromDataBase(
        city: String
    ) {
        cityDao?.deleteCity(
            city
        )
    }

    suspend fun insertInDataBase(
        city: City
    ) {
        cityDao?.insert(
            city
        )
    }

    fun getAllCity(): LiveData<List<City>>? {
        return cityDao?.getAllSavedCity()
    }

}
