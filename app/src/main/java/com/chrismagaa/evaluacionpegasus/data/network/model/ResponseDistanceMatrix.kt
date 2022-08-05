package com.chrismagaa.evaluacionpegasus.data.network.model

data class ResponseDistanceMatrix(
    val destination_addresses: List<String>,
    val origin_addresses: List<String>,
    val rows: List<Row>,
    val status: String
)