package com.sushil.mausam.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


private const val UNIT_TYPE = "unit_type"

class PreferenceProviderMausam(
    context: Context
) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun getUnitType(): String {
        return preference.getString(UNIT_TYPE, "").toString()
    }

    fun updateUnitType(unitType: String) {
        preference.edit().putString(UNIT_TYPE, unitType).apply()
    }

    fun clearAll() {
        preference.edit().clear().apply()
    }
}