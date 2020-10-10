package com.sushil.mausam.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sushil.mausam.R
import com.sushil.mausam.customviews.SwipeToDeleteCallback
import com.sushil.mausam.database.City
import com.sushil.mausam.ui.home.adapter.CityAdapter
import com.sushil.mausam.ui.map.MapsActivity
import com.sushil.mausam.utils.pushToNext
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var mFilterMenuItem: MenuItem? = null
    private var adapter: CityAdapter? = null
    private var savedCityList: MutableList<City?> = mutableListOf()
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
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(requireActivity()) {
                override fun onSwiped(
                    @NonNull viewHolder: RecyclerView.ViewHolder,
                    i: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val dialogBuilder = AlertDialog.Builder(context)

                    dialogBuilder.setMessage("Are you sure you want to delete \"${(savedCityList[position]?.city)}\" ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                            // deleteSavedSearch(savedCityList[position])
                        })
                        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                            adapter?.notifyDataSetChanged()
                            dialog.cancel()

                        })

                    val alert = dialogBuilder.create()
                    alert.setTitle("Delete Saved Search")

                    alert.show()
                }
            }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(recyclerViewCity)
    }

    /*private fun setAdapter() {
           adapter = UserLocationListAdapter(object : UserLocationListAdapter.onItemClickListener{
               override fun onItemClick(city: City) {


               override fun onItemDeleteClick(city: String) {
                   cityViewModel.deleteUserCity(city)
               }

           })
           recyclerViewCity.adapter = adapter

           getAllCity()
       }*/
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
}