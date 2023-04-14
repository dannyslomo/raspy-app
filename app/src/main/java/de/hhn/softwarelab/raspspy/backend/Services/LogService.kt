package de.hhn.softwarelab.raspspy.backend.Services

import de.hhn.softwarelab.raspspy.backend.RetrofitClient
import de.hhn.softwarelab.raspspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspspy.backend.interfaces.LogApi

class LogService {
    private val retrofit = RetrofitClient.getClient()
    private val logApi = retrofit.create(LogApi::class.java)

    var getSuccessful : Boolean? = null
    var getHttpStatusCode : Int? = null
    var getHttpStatusMessage : String? = null
    var getBody: List<ImageLog>? = null

    var postSuccessful : Boolean? = null
    var postHttpStatusCode : Int? = null
    var postHttpStatusMessage : String? = null
    var postBody: List<ImageLog>? = null

    fun getLogs(){
        val settingsResponse = logApi.getLogs().execute()
        getSuccessful = settingsResponse.isSuccessful
        getHttpStatusCode = settingsResponse.code()
        getHttpStatusMessage = settingsResponse.message()
        getBody = settingsResponse.body()
    }

    fun postLogs(log: ImageLog){
        val settingsResponse = logApi.postLog(log).execute()
        postSuccessful = settingsResponse.isSuccessful
        postHttpStatusCode = settingsResponse.code()
        postHttpStatusMessage = settingsResponse.message()
        postBody = settingsResponse.body()
    }
}