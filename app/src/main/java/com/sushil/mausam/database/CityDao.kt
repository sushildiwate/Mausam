package com.sushil.mausam.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: List<City>)


    @Query("DELETE FROM  table_city WHERE city  = :city ")
    fun deleteCity(city: String)


    @Query("SELECT * FROM table_city")
    fun getAllSavedCity(): LiveData<List<City>>
}