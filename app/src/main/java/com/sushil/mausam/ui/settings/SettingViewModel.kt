package com.sushil.mausam.ui.settings

import androidx.lifecycle.ViewModel

class SettingViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    suspend fun deleteAllCitiesFromDataBase(
    ) {
        settingRepository.deleteAllCitiesFromDataBase()
    }
}