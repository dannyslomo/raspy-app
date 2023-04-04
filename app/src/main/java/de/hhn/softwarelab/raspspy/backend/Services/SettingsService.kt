package de.hhn.softwarelab.raspspy.backend.Services

import de.hhn.softwarelab.raspspy.backend.RetrofitClient
import de.hhn.softwarelab.raspspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspspy.backend.interfaces.SettingsApi
import kotlin.properties.Delegates


class SettingsService {
    private val retrofit = RetrofitClient.getClient()
    private val settingsApi = retrofit.create(SettingsApi::class.java)

    val settingsResponse = settingsApi.getSettings().execute()
    val successful = settingsResponse.isSuccessful
    val httpStatusCode = settingsResponse.code()
    val httpStatusMessage = settingsResponse.message()
    val body: List<Settings>? = settingsResponse.body()
}
