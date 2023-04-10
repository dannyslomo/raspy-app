package de.hhn.softwarelab.raspspy.backend.Services

import de.hhn.softwarelab.raspspy.backend.RetrofitClient
import de.hhn.softwarelab.raspspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspspy.backend.interfaces.SettingsApi
import kotlin.properties.Delegates


class SettingsService {
    private val retrofit = RetrofitClient.getClient()
    private val settingsApi = retrofit.create(SettingsApi::class.java)

    var getSuccessful : Boolean? = null
    var getHttpStatusCode : Int? = null
    var getHttpStatusMessage : String? = null
    var getBody: List<Settings>? = null

    var postSuccessful : Boolean? = null
    var postHttpStatusCode : Int? = null
    var postHttpStatusMessage : String? = null
    var postBody: List<Settings>? = null
    fun getSettings(){
        val settingsResponse = settingsApi.getSettings().execute()
        getSuccessful = settingsResponse.isSuccessful
        getHttpStatusCode = settingsResponse.code()
        getHttpStatusMessage = settingsResponse.message()
        getBody = settingsResponse.body()
    }

    fun postSettings(settings: Settings){
        val settingsResponse = settingsApi.postSettings(settings).execute()
        postSuccessful = settingsResponse.isSuccessful
        postHttpStatusCode = settingsResponse.code()
        postHttpStatusMessage = settingsResponse.message()
        postBody = settingsResponse.body()
    }

}
