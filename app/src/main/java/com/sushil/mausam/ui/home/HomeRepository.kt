package com.sushil.mausam.ui.home

import androidx.lifecycle.LiveData
import com.sushil.mausam.MausamApplication
import com.sushil.mausam.database.City
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Repository for Home screen
 * */
class HomeRepository {
    private val cityDao = MausamApplication.instance?.getRoomDAO()?.cityDao()
    private val mExecutor: Executor = Executors.newSingleThreadExecutor()

    fun insert(city: City) {
        mExecutor.execute(Runnable { cityDao?.insert(city) })
    }

    /*fun delete(userCity: String) {
        mExecutor.execute(Runnable { cityDao?.deleteCity(userCity) })
    }*/

    //val allCity: LiveData<List<City>>? = cityDao?.getAllSavedCity()
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
