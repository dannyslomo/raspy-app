package de.hhn.softwarelab.raspy.backend.Services

import de.hhn.softwarelab.raspy.backend.RetrofitClient
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.backend.interfaces.SettingsApi


class SettingsService {
    private val retrofit = RetrofitClient.getClient()
    private val settingsApi = retrofit.create(SettingsApi::class.java)

    var successful : Boolean? = null
    var httpStatusCode : Int? = null
    var httpStatusMessage : String? = null

    var getBody: List<Settings>? = null
    var postBody: Settings? = null
    var putBody: Settings? = null

    fun getSettings(){
        val settingsResponse = settingsApi.getSettings().execute()
        successful = settingsResponse.isSuccessful
        httpStatusCode = settingsResponse.code()
        httpStatusMessage = settingsResponse.message()
        getBody = settingsResponse.body()
    }

    fun postSettings(settings: Settings){
        val settingsResponse = settingsApi.postSettings(settings).execute()
        successful = settingsResponse.isSuccessful
        httpStatusCode = settingsResponse.code()
        httpStatusMessage = settingsResponse.message()
        postBody = settingsResponse.body()
    }

    fun putSettings(settings: Settings, settingsId: Int){
        val settingsResponse = settingsApi.putSettings(settings, settingsId).execute()
        successful = settingsResponse.isSuccessful
        httpStatusCode = settingsResponse.code()
        httpStatusMessage = settingsResponse.message()
        putBody = settingsResponse.body()
    }

}
