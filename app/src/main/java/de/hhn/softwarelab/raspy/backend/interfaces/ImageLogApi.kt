package de.hhn.softwarelab.raspy.backend.interfaces

import retrofit2.Call
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import retrofit2.http.*

interface ImageLogApi {
    @GET("image/")
    fun getLogs(): Call<List<ImageLog>>

    @POST("image/post/")
    fun postLog(@Body log: ImageLog): Call<ImageLog>

    @DELETE("image/delete/{logId}/")
    fun deleteLog(@Path("logId") logId: Int): Call<ImageLog>
}