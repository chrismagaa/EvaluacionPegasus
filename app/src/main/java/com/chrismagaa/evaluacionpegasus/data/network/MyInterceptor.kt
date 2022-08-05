package com.chrismagaa.evaluacionpegasus.data.network

import com.chrismagaa.evaluacionpegasus.Constants
import okhttp3.Interceptor
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        return if(isInternetAvailable()){
            //Verificamos si hay internet
            val original = chain.request()
            val originalHttpUrl = original.url()


            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", Constants.MAPS_API_KEY)
                .addQueryParameter("units", "metric")
                .build()

            val requestBuilder = original.newBuilder().url(url)
            chain.proceed(requestBuilder.build())
        }else{
            throw NoInternetException()
        }
    }


    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockaddr, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }
    }

    class NoInternetException() : IOException() {
        override val message: String
            get() =
                "Internet no disponible, verifica tu conexión a internet"
    }
}
