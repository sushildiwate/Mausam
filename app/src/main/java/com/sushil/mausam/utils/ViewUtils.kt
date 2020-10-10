package com.sushil.mausam.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.sushil.mausam.R


fun Context.toast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun pushToNext(activity: Activity, intent: Intent?) {
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left)
}

fun pushToBack(activity: Activity) {
    activity.finish()
    activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right)
}


fun isNullOrEmptyString(str: String?): Boolean {
    if (str != null && str.isNotEmpty() && str != "" && str != " ")
        return false
    return true
}

fun isInternetAvailable(activity: Activity): Boolean {
    var result = false
    val connectivityManager =
        activity.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.let {
        it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    }
    return result
}


