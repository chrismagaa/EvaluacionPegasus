package com.chrismagaa.evaluacionpegasus.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chrismagaa.evaluacionpegasus.AddressRepository
import com.chrismagaa.evaluacionpegasus.data.local.Address

class MapsViewModel(private val repository: AddressRepository): ViewModel() {
    val allAddress: LiveData<List<Address>> = repository.allAddress


    class MapsViewModelFactory(private val repository: AddressRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}