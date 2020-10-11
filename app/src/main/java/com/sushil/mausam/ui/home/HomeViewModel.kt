package com.sushil.mausam.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sushil.mausam.database.City

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    suspend fun deleteCityFromDataBase(
        city: String
    ) {
        homeRepository.deleteCityFromDataBase(city)
    }
    suspend fun insertCityInDataBase(
        city: City
    ) {
        homeRepository.insertInDataBase(city)
    }

    fun getAllSavedCity(): LiveData<List<City>>? {
       return homeRepository.getAllCity()
    }

}