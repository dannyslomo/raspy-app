package de.hhn.softwarelab.raspy.backend.Services

import com.google.android.exoplayer2.util.Log
import de.hhn.softwarelab.raspy.backend.RetrofitClient
import de.hhn.softwarelab.raspy.backend.dataclasses.User
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.backend.interfaces.UserApi
import java.net.ConnectException

class UserService {
    private val retrofit = RetrofitClient.getClient()
    private val userApi = retrofit.create(UserApi::class.java)

    var successful : Boolean? = null
    var httpStatusCode : Int? = null
    var httpStatusMessage : String? = null

    var getBody: User? = null
    var postBody: User? = null

    fun register(user: User){
        Thread(Runnable {
            try{
                val userResponse = userApi.register(user).execute()
                successful = userResponse.isSuccessful
                httpStatusCode = userResponse.code()
                httpStatusMessage = userResponse.message()
                postBody = userResponse.body()
                //Successfully connected to REST API
                if (successful == true) {
                    println("postMessage: " + httpStatusMessage)
                    println("postCode: " + httpStatusCode)

                    println(postBody?.settingsId)
                    globalValues.settingsId = postBody?.settingsId?.toInt()!!
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

    fun login(user: User){
        Thread(Runnable {
            try {
                val userResponse = userApi.login(user).execute()
                successful = userResponse.isSuccessful
                httpStatusCode = userResponse.code()
                httpStatusMessage = userResponse.message()
                getBody = userResponse.body()

                //Successfully connected to REST API
                if (successful == true) {
                    println("postMessage: " + httpStatusMessage)
                    println("postCode: " + httpStatusCode)

                    println(getBody?.settingsId)
                    globalValues.settingsId = getBody?.settingsId?.toInt()!!
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
}