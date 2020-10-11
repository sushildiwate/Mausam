package com.sushil.mausam.ui.map

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sushil.mausam.R
import com.sushil.mausam.database.City
import com.sushil.mausam.ui.home.HomeRepository
import com.sushil.mausam.ui.home.HomeViewModel
import com.sushil.mausam.utils.Coroutines
import com.sushil.mausam.utils.pushToBack
import com.sushil.mausam.utils.toast
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeRepository: HomeRepository

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val PLACE_PICKER_REQUEST = 3

    }

    private lateinit var lastLocation: Location
    private lateinit var selectedLocation: LatLng
    private var selectedCityName: String = ""
    private var selectedAddress: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        homeRepository = HomeRepository()
        homeViewModel = HomeViewModel(homeRepository)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        /*text_view_search.setOnClickListener {
            loadPlacePicker()
        }*/
        text_view_bookmark.setOnClickListener {

            if (selectedCityName.isEmpty()) {
                getAddress(selectedLocation)
            }

            var city = City(0,
                selectedCityName,
                selectedAddress,
                selectedLocation.latitude,
                selectedLocation.longitude
            )
            Coroutines.io { homeViewModel.insertCityInDataBase(city,this) }

            toast("Bookmarked Successfully")

        }
        image_view_back_arrow.setOnClickListener { pushToBack(this) }
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        selectedLocation = location
        val markerOptions = MarkerOptions().position(location)
        val titleStr = getAddress(location)
        markerOptions.title(titleStr)
        mMap.addMarker(markerOptions).showInfoWindow()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)


        mMap.setOnMapClickListener {
            toast(it.toString())
            //allPoints.add(it)
            mMap.clear()
            placeMarkerOnMap(it)
        }
        setUpMap()

        mMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            true
        }

    }

    override fun onMarkerClick(p0: Marker?) = true

    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && addresses.isNotEmpty()) {

                val address =
                    addresses[0].getAddressLine(0) //0 to obtain first possible address

                val city = addresses[0].subAdminArea
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode

                addressText = "$address-$city-$state"
                city?.let { selectedCityName = city }
                address?.let { selectedAddress = address }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
            selectedAddress = ""
            selectedCityName = ""
        }

        return addressText
    }

    /*private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()
        try {
            startActivityForResult(builder.build(this@MapsActivity), PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                var addressText = place.name.toString()
                addressText += "\n" + place.address.toString()
                mMap.clear()
                placeMarkerOnMap(place.latLng)
            }
        }

    }*/

}