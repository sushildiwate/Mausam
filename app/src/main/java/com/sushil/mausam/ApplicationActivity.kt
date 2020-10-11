package com.sushil.mausam

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sushil.mausam.utils.isInternetAvailable
import com.sushil.mausam.utils.ConnectivityReceiver
import kotlinx.android.synthetic.main.activity_weather.*


open class ApplicationActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {

    private fun showMessage(isConnected: Boolean) {
        Log.e("ïsConnection", isConnected.toString())
        if (!isConnected) {
            include_layout_snackbar.visibility = View.VISIBLE
            window.statusBarColor = ContextCompat.getColor(this, R.color.monza)

            include_layout_snackbar.setOnClickListener {
                AlertDialog.Builder(this)
                    .setTitle("Mausam")
                    .setMessage("No Internet Connection. Please enable/check your internet to use the application")
                    .setNegativeButton(
                        "Ok"
                    ) { _, _ ->

                    }.show()
            }
        } else {
            include_layout_snackbar.visibility = View.GONE
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }


    }

    override fun onResume() {
        super.onResume()
        try {
            if (!isInternetAvailable(this)) {
                showMessage(false)
            }
            this.registerReceiver(
                ConnectivityReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } catch (e: Exception) {
        }

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        try {
            this.unregisterReceiver(ConnectivityReceiver())
        } catch (e: Exception) {
        }
        super.onPause()
    }

    override fun onDestroy() {
        try {
            this.unregisterReceiver(ConnectivityReceiver())
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    override fun onStop() {

        try {
            this.unregisterReceiver(ConnectivityReceiver())
        } catch (e: Exception) {
        }
        super.onStop()
    }

    /**
     * Callback will be called when there is change
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}
