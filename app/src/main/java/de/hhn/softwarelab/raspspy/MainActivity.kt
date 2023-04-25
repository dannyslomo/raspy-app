package de.hhn.softwarelab.raspspy


import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RingVolume
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import de.hhn.softwarelab.raspspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspspy.notification.NotificationUtils
import de.hhn.softwarelab.raspspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.*
import java.net.ConnectException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Access the system service in onCreate()
        val notificationUtils = NotificationUtils()
        notificationUtils.checkNotificationPermission(this)
        notificationUtils.getNotification(1, this)

        setContent {
            RaspSPYTheme {
        }
    }
}

@Composable
fun StandardButton(
    text: String,
    onClick: () -> Unit = {}
){
    Button(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RaspSPYTheme {
    }
}

fun getLogs(){
    Thread(Runnable {
        try {
            val logService = ImageLogService()
            logService.getLogs()

            //Successfully connected to REST API
            if (logService.successful == true) {
                println("postMessage: " + logService.httpStatusMessage)
                println("postCode: " + logService.httpStatusCode)

                logService.getBody?.forEach { log ->
                    println("" + log.timeStamp + ", " + log.triggerType)
                }
                //Error while connecting to REST API
            } else {
                when (logService.httpStatusCode) {
                    404 -> Log.e("Rest Connection", "404 Not Found")
                    405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                    400 -> Log.e("Rest Connection", "400 Bad Request")
                    500 -> Log.e("Rest Connection", "500 Internal Server Error")
                }
            }
            //Error while connecting to REST API
        } catch (e: ConnectException) {
            Log.e("Rest Connection", "Connection Error")
        } catch (e: Exception){
            Log.e("Rest Connection", e.message.toString())
        }
    }).start()
}

fun postLog(){
    Thread(Runnable {
        try {
            val logService = ImageLogService()
            logService.postLogs(ImageLog(LocalDateTime.now(), 2))

            //Successfully connected to REST API
            if(logService.successful == true) {
                println("postBody: " + logService.postBody)
                println("postSuccessful: " + logService.successful)
                println("postMessage: " + logService.httpStatusMessage)
                println("postCode: " + logService.httpStatusCode)
            }
            //Error while connecting to REST API
            else{
                when (logService.httpStatusCode) {
                    404 -> Log.e("Rest Connection", "404 Not Found")
                    405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                    400 -> Log.e("Rest Connection", "400 Bad Request")
                    500 -> Log.e("Rest Connection", "500 Internal Server Error")
                }
            }
        }
        //Error while connecting to REST API
        catch (e: ConnectException) {
            Log.e("Rest Connection", "Connection Error")
        }
        //Error while connecting to REST API
        catch (e: Exception){
            Log.e("Rest Connection", e.message.toString())
        }
    }).start()
}

fun putLog(){
    Thread(Runnable {
        try {
            val logService = ImageLogService()
            logService.putLogs(ImageLog(LocalDateTime.now(), 2) ,"1")

            //Successfully connected to REST API
            if(logService.successful == true) {
                println("postBody: " + logService.postBody)
                println("postSuccessful: " + logService.successful)
                println("postMessage: " + logService.httpStatusMessage)
                println("postCode: " + logService.httpStatusCode)
            }
            //Error while connecting to REST API
            else{
                when (logService.httpStatusCode) {
                    404 -> Log.e("Rest Connection", "404 Not Found")
                    405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                    400 -> Log.e("Rest Connection", "400 Bad Request")
                    500 -> Log.e("Rest Connection", "500 Internal Server Error")
                }
            }
        }
        //Error while connecting to REST API
        catch (e: ConnectException) {
            Log.e("Rest Connection", "Connection Error")
        }
        //Error while connecting to REST API
        catch (e: Exception){
            Log.e("Rest Connection", e.message.toString())
        }
    }).start()
}


fun getSettings() {
    Thread(Runnable {
        try {
            val settingsService = SettingsService()
            settingsService.getSettings()

            //Successfully connected to REST API
            if (settingsService.successful == true) {
                println("postMessage: " + settingsService.httpStatusMessage)
                println("postCode: " + settingsService.httpStatusCode)

                settingsService.getBody?.forEach { setting ->
                    println("" + setting.cameraActive + ", " + setting.systemActive + ", " + setting.deleteInterval)
                }
            //Error while connecting to REST API
            } else {
                println(settingsService.getBody)
                when (settingsService.httpStatusCode) {
                    404 -> Log.e("Rest Connection", "404 Not Found")
                    405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                    400 -> Log.e("Rest Connection", "400 Bad Request")
                    500 -> Log.e("Rest Connection", "500 Internal Server Error")
                }
            }
        //Error while connecting to REST API
        } catch (e: ConnectException) {
            Log.e("Rest Connection", "Connection Error")
        } catch (e: Exception){
            Log.e("Rest Connection", e.message.toString())
        }
    }).start()
}


fun postSettings(){
    Thread(Runnable {
        try {
            val settingsService = SettingsService()
            settingsService.postSettings(Settings(5,true,true))

            //Successfully connected to REST API
            if(settingsService.successful == true) {
                println("postMessage: " + settingsService.httpStatusMessage)
                println("postCode: " + settingsService.httpStatusCode)

                settingsService.getBody?.forEach { setting ->
                    println("" + setting.cameraActive + ", " + setting.systemActive + ", " + setting.deleteInterval)
                }
            }
            //Error while connecting to REST API
            else{
                when (settingsService.httpStatusCode) {
                    404 -> Log.e("Rest Connection", "404 Not Found")
                    405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                    400 -> Log.e("Rest Connection", "400 Bad Request")
                    500 -> Log.e("Rest Connection", "500 Internal Server Error")
                }
            }
        }
        //Error while connecting to REST API
        catch (e: ConnectException) {
            Log.e("Rest Connection", "Connection Error")
        }
        //Error while connecting to REST API
        catch (e: Exception){
            Log.e("Rest Connection", e.message.toString())
        }
    }).start()
}

fun putSettings(){
    Thread(Runnable {
        try {
            val settingsService = SettingsService()
            settingsService.putSettings(Settings(5,true,true), 28)

            //Successfully connected to REST API
            if(settingsService.successful == true) {
                println("postMessage: " + settingsService.httpStatusMessage)
                println("postCode: " + settingsService.httpStatusCode)

                settingsService.getBody?.forEach { setting ->
                    println("" + setting.cameraActive + ", " + setting.systemActive + ", " + setting.deleteInterval)
                }
            }
            //Error while connecting to REST API
            else{
                when (settingsService.httpStatusCode) {
                    404 -> Log.e("Rest Connection", "404 Not Found")
                    405 -> Log.e("Rest Connection", "405 Method Not Allowed")
                    400 -> Log.e("Rest Connection", "400 Bad Request")
                    500 -> Log.e("Rest Connection", "500 Internal Server Error")
                }
            }
        }
        //Error while connecting to REST API
        catch (e: ConnectException) {
            Log.e("Rest Connection", "Connection Error")
        }
        //Error while connecting to REST API
        catch (e: Exception){
            Log.e("Rest Connection", e.message.toString())
        }
    }).start()
}





