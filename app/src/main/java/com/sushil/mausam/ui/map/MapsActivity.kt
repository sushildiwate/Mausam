package com.sushil.mausam.ui.map

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
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
import com.sushil.mausam.MausamApplication
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
    }

    private lateinit var lastLocation: Location
    private lateinit var selectedLocation: LatLng
    private var selectedCityName: String = ""
    private var selectedAddress: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val cityDao = MausamApplication.instance?.getRoomDAO()?.cityDao()
        homeRepository = HomeRepository(cityDao)
        homeViewModel = HomeViewModel(homeRepository)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        text_view_bookmark.setOnClickListener {

            if (selectedCityName.isEmpty()) {
                getAddress(selectedLocation)
            }

            val city = City(
                0,
                selectedCityName,
                selectedAddress,
                selectedLocation.latitude,
                selectedLocation.longitude
            )
            Coroutines.io { homeViewModel.insertCityInDataBase(city) }
            toast("Bookmarked Successfully")
            finish()
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)


        mMap.setOnMapClickListener {
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
        val geoCoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        var addressText = ""

        try {
            addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address =
                    addresses[0].getAddressLine(0) //0 to obtain first possible address

                val city = addresses[0].subAdminArea
                val state = addresses[0].adminArea

                addressText = "$address-$city-$state"
                city?.let { selectedCityName = city }
                address?.let { selectedAddress = address }
            } else toast("No address found")

        } catch (e: IOException) {
            e.printStackTrace().toString()
            selectedAddress = ""
            selectedCityName = ""
        }

        return addressText
    }

}