package de.hhn.softwarelab.raspy.backend.interfaces

import retrofit2.Call
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import retrofit2.http.*

interface ImageLogApi {
    @GET("image/")
    fun getLogs(): Call<List<ImageLog>>

    @POST("image/")
    fun postLog(@Body log: ImageLog): Call<ImageLog>

    @PUT("image/update/{logId}/")
    fun putLog(@Body log: ImageLog, @Path("logId") logId: Int): Call<ImageLog>
}