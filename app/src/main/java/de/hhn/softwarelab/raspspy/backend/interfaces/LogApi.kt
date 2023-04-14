package de.hhn.softwarelab.raspspy.backend.interfaces

import retrofit2.Call
import retrofit2.http.GET
import de.hhn.softwarelab.raspspy.backend.dataclasses.ImageLog
import retrofit2.http.Body
import retrofit2.http.POST

interface LogApi {
    @GET("logs")
    fun getLogs(): Call<List<ImageLog>>

    @POST("logs")
    fun postLog(@Body log: ImageLog): Call<List<ImageLog>>
}