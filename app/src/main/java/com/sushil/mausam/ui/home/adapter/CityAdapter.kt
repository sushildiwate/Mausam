package com.sushil.mausam.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushil.mausam.R
import com.sushil.mausam.model.CityModel
import kotlinx.android.synthetic.main.list_item_city.view.*


class CityAdapter(
    var itemClickListener: onItemClickListener
) : RecyclerView.Adapter<CityAdapter.InvolveUserClaimViewHolder>() {

    private var cityList = emptyList<CityModel>() // Cached copy of words

    interface onItemClickListener {
        fun onItemClick(
            city: CityModel
        )
        fun onItemDeleteClick(
            city: String
        )
    }

    internal fun setCity(cityList: List<CityModel>) {
        this.cityList = cityList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InvolveUserClaimViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_city, parent, false)
    )
    override fun getItemCount() = cityList.size

    override fun onBindViewHolder(holder: InvolveUserClaimViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    inner class InvolveUserClaimViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {


        fun bind(cityModel: CityModel) {
            itemView.city.text = cityModel.city
            itemView.address.text = cityModel.address


            itemView.cardViewItem.setOnClickListener {
                itemClickListener.onItemClick(cityModel)
            }

            /*itemView.img_delete.setOnClickListener {
                itemClickListener.onItemDeleteClick(City.city)
            }*/


        }


    }

}