package com.sushil.mausam.ui.settings

import com.sushil.mausam.database.CityDao


/**
 * Repository for Setting screen
 * */
class SettingRepository(private val cityDao: CityDao?) {

    suspend fun deleteAllCitiesFromDataBase() {
        cityDao?.nukeAll(
        )
    }
    suspend fun getBookMarkCount():Int? {
        return cityDao?.getCount()
    }
}
