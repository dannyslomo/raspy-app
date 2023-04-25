package de.hhn.softwarelab.raspspy.backend.Services

import de.hhn.softwarelab.raspspy.backend.RetrofitClient
import de.hhn.softwarelab.raspspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspspy.backend.interfaces.ImageLogApi

class ImageLogService {
    private val retrofit = RetrofitClient.getClient()
    private val logApi = retrofit.create(ImageLogApi::class.java)

    var successful : Boolean? = null
    var httpStatusCode : Int? = null
    var httpStatusMessage : String? = null

    var getBody: List<ImageLog>? = null
    var postBody: ImageLog? = null
    var putBody: ImageLog? = null

    fun getLogs(){
        val settingsResponse = logApi.getLogs().execute()
        successful = settingsResponse.isSuccessful
        httpStatusCode = settingsResponse.code()
        httpStatusMessage = settingsResponse.message()
        getBody = settingsResponse.body()
    }

    fun postLogs(log: ImageLog){
        val settingsResponse = logApi.postLog(log).execute()
        successful = settingsResponse.isSuccessful
        httpStatusCode = settingsResponse.code()
        httpStatusMessage = settingsResponse.message()
        postBody = settingsResponse.body()
    }

    fun putLogs(log: ImageLog, url: String){
        val settingsResponse = logApi.putLog(log, url).execute()
        successful = settingsResponse.isSuccessful
        httpStatusCode = settingsResponse.code()
        httpStatusMessage = settingsResponse.message()
        putBody = settingsResponse.body()
    }
}