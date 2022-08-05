package com.chrismagaa.evaluacionpegasus.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.chrismagaa.evaluacionpegasus.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chrismagaa.evaluacionpegasus.ui.maps.MapsActivity
import com.chrismagaa.evaluacionpegasus.MyApp
import com.chrismagaa.evaluacionpegasus.data.local.Address
import com.chrismagaa.evaluacionpegasus.ui.AddressAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private val vmMain: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((application as MyApp).repository)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AddressAdapter
    private var myLocation: Location? = null
    var listAddress: List<Address> = ArrayList()
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLocation()
        setupRecyclerView()
        initObserver()

        binding.btnAgregar.setOnClickListener {
            val latitud = binding.etLatitud.text.toString()
            val longitud = binding.etLongitud.text.toString()

            if(myLocation != null) {
                //Validar que se halla obtenido la localización
                if (latitud.isNotBlank() && longitud.isNotBlank()) {
                    //Validar que los campos no esten vacios
                    vmMain.getDistance(myLocation!!, latitud, longitud)
                }else {Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show()}
            } else { Toast.makeText(this, "Es necesaria tu localización", Toast.LENGTH_LONG).show() }


        }

        binding.btnFinalizar.setOnClickListener {
            if(listAddress.size >= 5) {
                //Validar que sean minimo 5 direcciones
                goToMapsActivity()
            } else { Toast.makeText(this, "Se necesitan minimo 5 direcciones", Toast.LENGTH_SHORT).show() }

        }


    }

    private fun goToMapsActivity() {
        if (myLocation != null) {
            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra(MapsActivity.EXTRA_LOCATION, myLocation)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Es necesaria tu localización", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (isLocationPermissionGranted()) {
            //Si los permisos son aceptados, se obtiene la localización
            getMyLocation()
        } else {
            //Se solicita los permisos
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
    }

    private fun initObserver() {
        vmMain.allAddress.observe(this) {
            listAddress = it
            it?.let { adapter.setData(it) }
        }
    }

    private fun setupRecyclerView() {
        adapter = AddressAdapter {
            vmMain.deleteAddress(it)
        }

        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
    }


    private fun isLocationPermissionGranted(): Boolean = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getMyLocation()
            }
            else -> {
                Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getMyLocation() {
        fusedLocationProviderClient!!.lastLocation.addOnSuccessListener { location ->
            location?.let {
                myLocation = it
            }

        }.addOnFailureListener { e ->
            Log.e(this.javaClass.simpleName, e.message, e)
            Toast.makeText(this, "No fue posible obtener tu localización, activa tu ubicación.", Toast.LENGTH_LONG).show()
        }
    }


}