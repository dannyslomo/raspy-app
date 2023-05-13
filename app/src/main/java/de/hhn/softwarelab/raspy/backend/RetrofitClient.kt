package de.hhn.softwarelab.raspy.backend

import de.hhn.softwarelab.raspy.backend.dataclasses.Url
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {
    val baseUrl = Url.serverUrl //Has to end with /           http://192.168.178.27:8000/

    val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(RequestInterceptor)
        .build()

    fun getClient(): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
}