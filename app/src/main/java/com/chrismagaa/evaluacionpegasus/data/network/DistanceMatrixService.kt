package com.chrismagaa.evaluacionpegasus.data.network

import com.chrismagaa.evaluacionpegasus.data.network.model.ResponseDistanceMatrix
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DistanceMatrixService {
    @GET("json")
    suspend fun getDistance(
        @Query("origins") origins: String,
        @Query("destinations") destinations: String,
    ) : Response<ResponseDistanceMatrix>
}