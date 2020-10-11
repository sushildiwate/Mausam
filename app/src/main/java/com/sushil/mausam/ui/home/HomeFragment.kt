package com.sushil.mausam.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sushil.mausam.R
import com.sushil.mausam.customviews.SwipeToDeleteCallback
import com.sushil.mausam.model.CityModel
import com.sushil.mausam.ui.weather.WeatherActivity
import com.sushil.mausam.ui.home.adapter.CityAdapter
import com.sushil.mausam.ui.map.MapsActivity
import com.sushil.mausam.utils.Coroutines
import com.sushil.mausam.utils.pushToNext
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), CityAdapter.CityClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeRepository: HomeRepository
    var mFilterMenuItem: MenuItem? = null
    private lateinit var adapter: CityAdapter
    private var savedCityList: MutableList<CityModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeRepository = HomeRepository()
        homeViewModel = HomeViewModel(homeRepository)
        adapter = CityAdapter(savedCityList)
        adapter.setOnItemClickListener(this)
        homeViewModel.getAllSavedCity()?.observe(viewLifecycleOwner, Observer {
            if (savedCityList.size > 0)
                savedCityList.clear()
            for (item in it) {
                savedCityList.add(CityModel(item.city, item.address, item.latitude, item.longitude))
                Log.d("Saved City", "${item.city} ${item.id}")
            }
            adapter.notifyDataSetChanged()
        })

        recyclerViewCity.adapter = adapter
        recyclerViewCity.layoutManager = LinearLayoutManager(this.requireContext())


        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(requireActivity()) {
                override fun onSwiped(
                    @NonNull viewHolder: RecyclerView.ViewHolder,
                    i: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val dialogBuilder = AlertDialog.Builder(context)

                    dialogBuilder.setMessage("Are you sure you want to delete \"${(savedCityList[position].city)}\" ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { _, _ ->
                            Coroutines.io { homeViewModel.deleteCityFromDataBase(savedCityList[position].city) }
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            adapter.notifyDataSetChanged()
                            dialog.cancel()
                        }

                    val alert = dialogBuilder.create()
                    alert.setTitle("Delete Saved Search")

                    alert.show()
                }
            }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(recyclerViewCity)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        mFilterMenuItem = menu.findItem(R.id.addCity)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addCity -> {
                val intent = Intent(activity, MapsActivity::class.java)
                activity?.let { pushToNext(it, intent) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(city: CityModel) {
        val intent = Intent(activity, WeatherActivity::class.java)
        intent.putExtra(getString(R.string.latitude), city.latitude)
        intent.putExtra(getString(R.string.longitude), city.longitude)
        activity?.let { pushToNext(it, intent) }
    }
}