package com.sushil.mausam.ui.weather.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushil.mausam.R
import com.sushil.mausam.model.WeatherModel
import com.sushil.mausam.utils.glideWith
import kotlinx.android.synthetic.main.list_item_weather.view.*


class ForecastAdapter(
    private val foreCastList: MutableList<WeatherModel.Forecast>,
    var context: Context
) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private lateinit var listener: ForecastClickListener

    interface ForecastClickListener {
        fun onItemClick(
            adapterPosition: Int
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_weather, parent, false)
    )

    override fun getItemCount() = foreCastList.size
    fun setOnItemClickListener(listener: ForecastClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foreCastList[position])
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(forecastModel: WeatherModel.Forecast) {
            if (forecastModel.isSelected){
                itemView.textViewListTemperature.typeface = Typeface.DEFAULT_BOLD
                itemView.textViewListClimate.typeface = Typeface.DEFAULT_BOLD
            }else{
                itemView.textViewListTemperature.typeface = Typeface.DEFAULT
                itemView.textViewListClimate.typeface = Typeface.DEFAULT
            }
            itemView.textViewListTemperature.text = "Temperature: ${forecastModel.getMinMaxTemp()}"
            itemView.textViewListClimate.text =
                "Climate: ${forecastModel.getForecastWeather().getWeatherDescription()}"
            itemView.textViewDate.text = forecastModel.getDayName()

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            var url =
                "http://openweathermap.org/img/wn/${forecastModel.getForecastWeather().icon}@4x.png"
            itemView.imageViewListWeatherIcon.glideWith(url, context)

        }


    }

}