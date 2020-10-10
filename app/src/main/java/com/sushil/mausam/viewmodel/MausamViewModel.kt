package com.sushil.mausam.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushil.mausam.model.WeatherModel
import com.sushil.mausam.repository.MausamRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class MausamViewModel(private val mausamRepository: MausamRepository) : ViewModel() {
    val responseLiveData = MutableLiveData<WeatherModel>()
    val progressBar = MutableLiveData<Int>()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private lateinit var subscription: Disposable

    fun getWeather(lat: Double, lon: Double, api_key: String,unit:String, context: Context) {

        subscription =
            mausamRepository.getWeatherFromAPI(lat, lon, api_key,unit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressBar.value = 0 }
                .doOnTerminate { progressBar.value = 1 }
                .subscribe(
                    { result ->
                        responseLiveData.value = result
                    },
                    { error ->
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                )

    }

}