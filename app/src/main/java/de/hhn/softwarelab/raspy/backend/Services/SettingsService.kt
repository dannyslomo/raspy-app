package de.hhn.softwarelab.raspy.backend.Services

import com.google.android.exoplayer2.util.Log
import de.hhn.softwarelab.raspy.backend.RetrofitClient
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.backend.interfaces.SettingsApi
import java.net.ConnectException


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
        Thread(Runnable {
            try {
                val settingsResponse = settingsApi.getSettings().execute()
                successful = settingsResponse.isSuccessful
                httpStatusCode = settingsResponse.code()
                httpStatusMessage = settingsResponse.message()
                getBody = settingsResponse.body()

                //Successfully connected to REST API
                if (successful == true) {
                    println("postMessage: " + httpStatusMessage)
                    println("postCode: " + httpStatusCode)

                    getBody?.forEach { setting ->
                        println("" + setting.cameraActive + ", " + setting.systemActive + ", " + setting.deleteInterval)
                    }
                    //Error while connecting to REST API
                } else {
                    println(getBody)
                    when (httpStatusCode) {
                        403 -> Log.e("Rest Connection", "403 Forbidden")
                        404 -> Log.e("Rest Connection", "404 Not Found")
                        405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                        400 -> Log.e("Rest Connection", "400 Bad Request")
                        500 -> Log.e("Rest Connection", "500 Internal Server Error")
                    }
                }
            //Error while connecting to REST API
            } catch (e: ConnectException) {
                Log.e("Rest Connection", "Connection Error")
            } catch (e: Exception) {
                Log.e("Rest Connection", e.message.toString())
            }
        }).start()
    }

    fun postSettings(settings: Settings){
        Thread(Runnable {
            try{
                val settingsResponse = settingsApi.postSettings(settings).execute()
                successful = settingsResponse.isSuccessful
                httpStatusCode = settingsResponse.code()
                httpStatusMessage = settingsResponse.message()
                postBody = settingsResponse.body()

                //Successfully connected to REST API
                if (successful == true) {
                    println("postMessage: " + httpStatusMessage)
                    println("postCode: " + httpStatusCode)

                    getBody?.forEach { setting ->
                        println("" + setting.cameraActive + ", " + setting.systemActive + ", " + setting.deleteInterval)
                    }
                }
                //Error while connecting to REST API
                else {
                    when (httpStatusCode) {
                        403 -> Log.e("Rest Connection", "403 Forbidden")
                        404 -> Log.e("Rest Connection", "404 Not Found")
                        405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                        400 -> Log.e("Rest Connection", "400 Bad Request")
                        500 -> Log.e("Rest Connection", "500 Internal Server Error")
                        else -> Log.e("Rest Connection", httpStatusCode.toString())
                    }
                }
            }
            //Error while connecting to REST API
            catch (e: ConnectException) {
                Log.e("Rest Connection", "Connection Error")
            }
            //Error while connecting to REST API
            catch (e: Exception) {
                Log.e("Rest Connection", e.message.toString())
            }
        }).start()
    }

    fun putSettings(settings: Settings, settingsId: Int){
        Thread(Runnable {
            try {
                val settingsResponse = settingsApi.putSettings(settings, settingsId).execute()
                successful = settingsResponse.isSuccessful
                httpStatusCode = settingsResponse.code()
                httpStatusMessage = settingsResponse.message()
                putBody = settingsResponse.body()

                //Successfully connected to REST API
                if (successful == true) {
                    println("postMessage: " + httpStatusMessage)
                    println("postCode: " + httpStatusCode)

                    getBody?.forEach { setting ->
                        println("" + setting.cameraActive + ", " + setting.systemActive + ", " + setting.deleteInterval)
                    }
                }
                //Error while connecting to REST API
                else {
                    when (httpStatusCode) {
                        403 -> Log.e("Rest Connection", "403 Forbidden")
                        404 -> Log.e("Rest Connection", "404 Not Found")
                        405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                        400 -> Log.e("Rest Connection", "400 Bad Request")
                        500 -> Log.e("Rest Connection", "500 Internal Server Error")
                        else -> Log.e("Rest Connection", httpStatusCode.toString())
                    }
                }
            }
            //Error while connecting to REST API
            catch (e: ConnectException) {
                Log.e("Rest Connection", "Connection Error")
            }
            //Error while connecting to REST API
            catch (e: Exception) {
                Log.e("Rest Connection", e.message.toString())
            }
        }).start()
    }

}
