package de.hhn.softwarelab.raspspy.backend.interfaces

import retrofit2.Call
import retrofit2.http.GET
import de.hhn.softwarelab.raspspy.backend.dataclasses.Image

interface ImageApi {
    @GET("images")
    fun getImages(): Call<List<Image>>
}