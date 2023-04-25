package de.hhn.softwarelab.raspy.backend.interfaces

import retrofit2.Call
import retrofit2.http.GET
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface ImageLogApi {
    @GET("image/")
    fun getLogs(): Call<List<ImageLog>>

    @POST("image/")
    fun postLog(@Body log: ImageLog): Call<ImageLog>

    @PUT("image/update/{logId}/")
    fun putLog(@Body log: ImageLog, @Url url: String): Call<ImageLog>
}