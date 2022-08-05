package com.chrismagaa.evaluacionpegasus.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var lat: String,
    var lng: String,
    var distance: String,
    var duration: String,
    var distanceValue: Int,)