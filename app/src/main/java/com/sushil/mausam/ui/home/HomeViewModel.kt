package com.sushil.mausam.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sushil.mausam.database.City

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    val allSavedCity: LiveData<List<City>> =  homeRepository.allCity

    fun deleteUserCity(userCity: String) {
        homeRepository.delete(userCity)
    }
}