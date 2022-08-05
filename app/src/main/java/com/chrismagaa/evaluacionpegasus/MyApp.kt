package com.chrismagaa.evaluacionpegasus

import android.app.Application
import com.chrismagaa.evaluacionpegasus.data.local.AppDatabase
import com.chrismagaa.evaluacionpegasus.data.network.DistanceMatrixClient

class MyApp: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AddressRepository(database.addressDao(), DistanceMatrixClient()) }
}