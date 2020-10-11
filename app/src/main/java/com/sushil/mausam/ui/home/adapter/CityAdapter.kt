package com.sushil.mausam.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushil.mausam.R
import com.sushil.mausam.model.CityModel
import kotlinx.android.synthetic.main.list_item_city.view.*


class CityAdapter(private val savedCityList: MutableList<CityModel>) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    private lateinit var listener: CityClickListener
    interface CityClickListener {
        fun onItemClick(
            city: CityModel
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_city, parent, false)
    )
    override fun getItemCount() = savedCityList.size
    fun setOnItemClickListener(listener: CityClickListener) {
        this.listener = listener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(savedCityList[position])
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {


        fun bind(cityModel: CityModel) {
            itemView.city.text = cityModel.city
            itemView.address.text = cityModel.address

            itemView.setOnClickListener {
                listener.onItemClick(cityModel)
            }

        }
    }
}