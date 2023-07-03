package de.hhn.softwarelab.raspy.backend.Services

import com.google.android.exoplayer2.util.Log
import de.hhn.softwarelab.raspy.backend.RetrofitClient
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.backend.interfaces.ImageLogApi
import java.net.ConnectException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ImageLogService {
        private val retrofit = RetrofitClient.getClient()
        private val logApi = retrofit.create(ImageLogApi::class.java)

        var successful : Boolean? = null
        var httpStatusCode : Int? = null
        var httpStatusMessage : String? = null

        var getBody: List<ImageLog>? = null
        var postBody: ImageLog? = null

        fun getImages(connectionCallback: (Boolean) -> Unit){
            Thread(Runnable {
                try {
                    val settingsResponse = logApi.getLogs().execute()
                    successful = settingsResponse.isSuccessful
                    httpStatusCode = settingsResponse.code()
                    httpStatusMessage = settingsResponse.message()
                    getBody = settingsResponse.body()

                    //Successfully connected to REST API
                    if (successful == true) {
                        println("postMessage: " + httpStatusMessage)
                        println("postCode: " + httpStatusCode)
                        connectionCallback(true)
                    } else {
                        connectionCallback(false)
                        when (httpStatusCode) {
                            403 -> Log.e("Rest Connection", "403 Forbidden")
                            404 -> Log.e("Rest Connection", "404 Not Found")
                            405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                            408 -> Log.e("Rest Connection", "408 Request Timeout")
                            400 -> Log.e("Rest Connection", "400 Bad Request")
                            500 -> Log.e("Rest Connection", "500 Internal Server Error")
                        }
                    }
                } catch (e: ConnectException) {
                    connectionCallback(false)
                    Log.e("Rest Connection", "Connection Error")
                } catch (e: Exception) {
                    connectionCallback(false)
                    Log.e("Rest Connection", e.message.toString())
                }
            }).start()
        }


    fun postImage(log: ImageLog){
        Thread(Runnable {
            try {
                val settingsResponse = logApi.postLog(log).execute()
                successful = settingsResponse.isSuccessful
                httpStatusCode = settingsResponse.code()
                httpStatusMessage = settingsResponse.message()
                postBody = settingsResponse.body()

                //Successfully connected to REST API
                if (successful == true) {
                    println("postBody: " + postBody)
                    println("postSuccessful: " + successful)
                    println("postMessage: " + httpStatusMessage)
                    println("postCode: " + httpStatusCode)
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
            }//Error while connecting to REST API
            catch (e: ConnectException) {
                Log.e("Rest Connection", "Connection Error")
            }
            //Error while connecting to REST API
            catch (e: Exception) {
                Log.e("Rest Connection", e.message.toString())
            }
        }).start()
    }

    //TODO: fix response deserialization error
    fun deleteImage(imageName: String) {
        Thread(Runnable {
            try{
                val settingsResponse = logApi.deleteLog(imageName).execute()
                successful = settingsResponse.isSuccessful
                //Successfully connected to REST API
                if (successful == true) {
                    println(successful)
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