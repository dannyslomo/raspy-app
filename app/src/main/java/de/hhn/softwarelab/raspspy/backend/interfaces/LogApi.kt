package de.hhn.softwarelab.raspspy.backend.interfaces

import retrofit2.Call
import retrofit2.http.GET
import de.hhn.softwarelab.raspspy.backend.dataclasses.Log
import retrofit2.http.POST

interface LogApi {
    @GET("logs")
    fun getLogs(): Call<List<Log>>
}