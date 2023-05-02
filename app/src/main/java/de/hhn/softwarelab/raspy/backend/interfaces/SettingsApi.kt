package de.hhn.softwarelab.raspy.backend.interfaces

import retrofit2.Call
import retrofit2.http.GET
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SettingsApi {
    @GET("settings/")
    fun getSettings(): Call<List<Settings>>

    @POST("settings/post/")
    fun postSettings(@Body settings: Settings): Call<Settings>

    @PUT("settings/update/{settingsId}/")
    fun putSettings(@Body settings: Settings, @Path("settingsId") settingsId: Int): Call<Settings>
}