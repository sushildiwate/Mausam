package com.sushil.mausam.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sushil.mausam.R
import com.sushil.mausam.viewmodel.MausamViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mMausamViewModel: MausamViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMausamViewModel.getWeather(18.52, 73.86, getString(R.string.api_key), "metric", this)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        mMausamViewModel.responseLiveData.observe(this, Observer { weather ->
            Log.d("RESPONSE",
                weather.getForecast()
                    .getDayName() + " " + weather.city + " " + " " + weather.getForecast().weather
            )
        })

        //Hide/show the progress bar
        mMausamViewModel.progressBar.observe(this, Observer { it ->
            if (it == 0) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
    }
}