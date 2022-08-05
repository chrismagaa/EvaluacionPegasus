package com.chrismagaa.evaluacionpegasus.data.network

import com.chrismagaa.evaluacionpegasus.data.network.model.ResponseDistanceMatrix

class DistanceMatrixClient {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getDistance(origin: String, destination: String) : ResponseDistanceMatrix?{
        return try{
            val response = retrofit.create(DistanceMatrixService::class.java).getDistance(origin, destination)
            response.body()
        }catch (e: MyInterceptor.NoInternetException){
            null
        }
    }
}