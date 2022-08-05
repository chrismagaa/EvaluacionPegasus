package com.chrismagaa.evaluacionpegasus

import androidx.lifecycle.LiveData
import com.chrismagaa.evaluacionpegasus.data.local.Address
import com.chrismagaa.evaluacionpegasus.data.local.AddressDao
import com.chrismagaa.evaluacionpegasus.data.network.DistanceMatrixClient
import com.chrismagaa.evaluacionpegasus.data.network.model.ResponseDistanceMatrix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddressRepository(private val addressDao: AddressDao, private val distanceMatrixClient: DistanceMatrixClient) {

    val  allAddress: LiveData<List<Address>> = addressDao.getAll()

    fun insert(address: Address){
        addressDao.insert(address)
    }
    fun delete(address: Address){
        addressDao.delete(address)
    }

    suspend fun getDistance(origin: String, destination: String): ResponseDistanceMatrix? {
        return withContext(Dispatchers.IO){
            distanceMatrixClient.getDistance(origin, destination)
        }
    }

}