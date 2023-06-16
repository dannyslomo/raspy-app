package de.hhn.softwarelab.raspy.backend.interfaces

import de.hhn.softwarelab.raspy.backend.dataclasses.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("user/register/")
    fun register(@Body user: User): Call<User>

    @POST("user/login/")
    fun login(@Body user: User): Call<User>
}