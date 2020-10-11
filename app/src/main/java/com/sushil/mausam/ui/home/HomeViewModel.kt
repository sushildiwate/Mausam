package com.sushil.mausam.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushil.mausam.database.City
import io.reactivex.disposables.Disposable

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private lateinit var subscription: Disposable

    val progressBar = MutableLiveData<Int>()

    suspend fun deleteCityFromDataBase(
        city: String
    ) {
        homeRepository.deleteCityFromDataBase(city)
    }
    suspend fun insertCityInDataBase(
        city: City, context: Context
    ) {
        homeRepository.insertInDataBase(city)
    }

    fun getAllSavedCity(): LiveData<List<City>>? {
       return homeRepository.getAllCity()
    }

}