package com.sushil.mausam.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.LottieAnimationView
import com.sushil.mausam.R
import com.sushil.mausam.utils.pushToNext
import com.sushil.myweatherapp.utils.PERMISSION_LOCATION


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var animationView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash_screen)
        animationView = findViewById(R.id.animation_view)

        animationView.playAnimation();
        Handler().postDelayed({
            animationView.cancelAnimation()
            if (checkPermissions()) {
                val intent = Intent(this, MainActivity::class.java)
                pushToNext(this, intent)
                finish()
            } else {
                requestPermissions()
            }

        }, 5000)
    }

    private fun checkPermissions(): Boolean {
        val permissionStateForCoras = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val permissionStateForFine =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        return (permissionStateForCoras == PackageManager.PERMISSION_GRANTED && permissionStateForFine == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_LOCATION) {

            val intent = Intent(this, MainActivity::class.java)
            pushToNext(this, intent)
            finish()

        }
    }
}
