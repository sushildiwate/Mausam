package com.sushil.mausam.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushil.mausam.R
import com.sushil.mausam.model.CityModel
import kotlinx.android.synthetic.main.list_item_city.view.*


class CityAdapter(private val savedCityList: MutableList<CityModel>) : RecyclerView.Adapter<CityAdapter.InvolveUserClaimViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(
            city: CityModel
        )
        fun onItemDeleteClick(
            city: String
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InvolveUserClaimViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_city, parent, false)
    )
    override fun getItemCount() = savedCityList.size

    override fun onBindViewHolder(holder: InvolveUserClaimViewHolder, position: Int) {
        holder.bind(savedCityList[position])
    }

    inner class InvolveUserClaimViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {


        fun bind(cityModel: CityModel) {
            itemView.city.text = cityModel.city
            itemView.address.text = cityModel.address


            /*itemView.img_delete.setOnClickListener {
                itemClickListener.onItemDeleteClick(City.city)
            }*/


        }


    }

}