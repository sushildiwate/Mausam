package com.sushil.mausam.utils

const val WEATHER_API_KEY = "886705b4c1182eb1c69f28eb8c520e20"
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

const val DEFAULT_LATITUDE=200.0
const val DEFAULT_LONGITUDE=200.0

const val UNIT_METRIC="metric"
const val UNIT_IMPERIAL="imperial"
const val UNIT_STANDARD=""