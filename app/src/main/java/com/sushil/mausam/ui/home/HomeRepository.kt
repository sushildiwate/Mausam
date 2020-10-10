package com.sushil.mausam.ui.home

import androidx.lifecycle.LiveData
import com.sushil.mausam.database.City
import com.sushil.mausam.database.CityDao
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Repository for Home screen
 * */
class HomeRepository(
    private val cityDao: CityDao
)  {
    private val mExecutor: Executor = Executors.newSingleThreadExecutor()

    fun insert(city: City) {
        mExecutor.execute(Runnable { cityDao.insert(city) })
    }

    fun delete(userCity: String) {
        mExecutor.execute(Runnable { cityDao.deleteCity(userCity) })
    }

    val allCity: LiveData<List<City>> = cityDao.getAllSavedCity()
}
