package com.sushil.mausam.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_city")
data class City(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val city: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)