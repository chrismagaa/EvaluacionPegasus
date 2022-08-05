package com.chrismagaa.evaluacionpegasus.ui.main

import android.location.Location
import androidx.lifecycle.*
import com.chrismagaa.evaluacionpegasus.AddressRepository
import com.chrismagaa.evaluacionpegasus.data.local.Address
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AddressRepository): ViewModel() {
    val allAddress: LiveData<List<Address>> = repository.allAddress

    fun insertAddress(lat: String, long: String, distance: String, duration: String, distanceValue: Int) {
            repository.insert(Address(0,lat, long, distance, duration, distanceValue))
    }

    fun getDistance(myLocation: Location, lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDistance("${myLocation.latitude}, ${myLocation.longitude}", "$lat,$long")
            response?.let {
                insertAddress(lat, long, it.rows[0].elements[0].distance.text?: "", it.rows[0].elements[0].duration.text?: "", it.rows[0].elements[0].distance.value)
            }
    }
    }

    fun deleteAddress(address: Address){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(address)
        }
    }

    class MainViewModelFactory(private val repository: AddressRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}