package com.chrismagaa.evaluacionpegasus.ui.maps

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.chrismagaa.evaluacionpegasus.MyApp
import com.chrismagaa.evaluacionpegasus.R
import com.chrismagaa.evaluacionpegasus.data.local.Address

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.chrismagaa.evaluacionpegasus.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        const val EXTRA_LOCATION = "extra_location"
    }

    private val vmMaps: MapsViewModel by viewModels {
        MapsViewModel.MapsViewModelFactory((application as MyApp).repository)
    }
    private var listAddress: List<Address> = ArrayList()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var myLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myLocation = intent.getParcelableExtra(EXTRA_LOCATION)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        vmMaps.allAddress.observe(this) {list ->
            list?.let { listAddress = it }


        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setMyLocationMarker()

        setListAddressMarker()

    }


    private fun setMyLocationMarker() {
        myLocation?.let { location ->
            val myLocation = LatLng(location.latitude, location.longitude)
            mMap.addMarker(MarkerOptions().position(myLocation).title("Yo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
        }
    }

    private fun setListAddressMarker() {
        listAddress.forEachIndexed {index, address ->
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(address.lat.toDouble(), address.lng.toDouble()))
                    .title((index+1).toString())
                    .snippet("${address.distance}, ${address.duration}")
            )
        }
    }

}