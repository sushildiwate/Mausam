package com.sushil.mausam.utils

const val PERMISSION_LOCATION = 22
const val API_TIMEOUT = 30L
const val DATABASE_NAME = "mausam_database.db"
const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
const val WEATHER_FORECAST = BASE_URL.plus("forecast/daily?cnt=6")

const val LATITUDE = "lat"
const val LONGITUDE = "lon"
const val APP_ID = "appid"
const val UNITS = "units"

var HOME = 0
var SETTINGS = 1
var HELP = 2