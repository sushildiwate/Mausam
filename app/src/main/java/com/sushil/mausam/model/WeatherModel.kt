package com.sushil.mausam.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.sushil.mausam.utils.capitalizeWords
import com.sushil.mausam.utils.getDayName

@Keep
data class WeatherModel(
    @SerializedName("city") val city: City = City(),
    @SerializedName("cnt") val cnt: Int = 0,
    @SerializedName("cod") val cod: String = "",
    @SerializedName("list") val list: List<Forecast> = listOf(),
    @SerializedName("message") val message: Double = 0.0
) {

    fun getForecast(): Forecast {
        return list.first()
    }

    @Keep
    data class City(
        @SerializedName("coord") val coord: Coord = Coord(),
        @SerializedName("country") val country: String = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("population") val population: Int = 0,
        @SerializedName("timezone") val timezone: Int = 0
    ) {
        @Keep
        data class Coord(
            @SerializedName("lat") val lat: Double = 0.0,
            @SerializedName("lon") val lon: Double = 0.0
        )
    }

    @Keep
    data class Forecast(
        @SerializedName("clouds") val clouds: Int = 0,
        @SerializedName("deg") val deg: Int = 0,
        @SerializedName("dt") val dt: Long = 0,
        @SerializedName("feels_like") val feelsLike: FeelsLike = FeelsLike(),
        @SerializedName("humidity") val humidity: Int = 0,
        @SerializedName("pop") val pop: Double = 0.0,
        @SerializedName("pressure") val pressure: Int = 0,
        @SerializedName("speed") val speed: Double = 0.0,
        @SerializedName("sunrise") val sunrise: Int = 0,
        @SerializedName("sunset") val sunset: Int = 0,
        @SerializedName("temp") val temp: Temp = Temp(),
        @SerializedName("weather") val weather: List<Weather> = listOf()
    ) {
        fun getDayName(): String {
            return dt.getDayName()
        }

        fun getMinMaxTemp(): String {
            return "%02d".format(temp.min.toInt()).plus("/")
                .plus("%02d".format(temp.max.toInt()))
        }

        fun getForecastWeather(): Weather {
            return weather.first()
        }
        fun getForecastWeatherList(): List<Weather> {
            return weather
        }
        fun getHumidity(): String {
            return humidity.toString().plus("%")
        }

        @Keep
        data class FeelsLike(
            @SerializedName("day") val day: Double = 0.0,
            @SerializedName("eve") val eve: Double = 0.0,
            @SerializedName("morn") val morn: Double = 0.0,
            @SerializedName("night") val night: Double = 0.0
        )

        @Keep
        data class Temp(
            @SerializedName("day") val day: Double = 0.0,
            @SerializedName("eve") val eve: Double = 0.0,
            @SerializedName("max") val max: Double = 0.0,
            @SerializedName("min") val min: Double = 0.0,
            @SerializedName("morn") val morn: Double = 0.0,
            @SerializedName("night") val night: Double = 0.0
        ) {
            fun getDayTemperature(): String {
                return day.toInt().toString()
            }
        }

        @Keep
        data class Weather(
            @SerializedName("description") val description: String = "",
            @SerializedName("icon") val icon: String = "",
            @SerializedName("id") val id: Int = 0,
            @SerializedName("main") val main: String = ""
        ) {
            fun getWeatherDescription(): String {
                return description.capitalizeWords()
            }

            fun getWeatherIcon(): String {
                return icon ?: ""
            }
        }
    }
}