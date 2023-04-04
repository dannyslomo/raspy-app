package de.hhn.softwarelab.raspspy.backend.interfaces

import retrofit2.Call
import retrofit2.http.GET
import de.hhn.softwarelab.raspspy.backend.dataclasses.Settings
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingsApi {
    @GET("settings")
    fun getSettings(): Call<List<Settings>>

    @POST("settings")
    fun postSettings(@Body settings: Settings): Call<List<Settings>>
}