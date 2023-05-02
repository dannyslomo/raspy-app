package de.hhn.softwarelab.raspy.ImageLogsUI

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
//import com.google.android.exoplayer2.util.Log
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import java.net.ConnectException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class LogsMainActivity : ComponentActivity() {


    var logList = mutableListOf<ImageLog>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val logComposables = LogComposables()
        getLogs()

        setContent {
            RaspSPYTheme {
                Column(){
                    logComposables.ScrollableLogs(logList)
                }
            }
        }
    }
    fun getLogs(){
        Thread(Runnable {
            try {
                val logsService = ImageLogService()
                ImageLogService().getLogs()

                //Successfully connected to REST API
                if (logsService.successful == true) {
                    println("postMessage: " + logsService.httpStatusMessage)
                    println("postCode: " + logsService.httpStatusCode)

                    logsService.getBody?.forEach { log ->
                        logList.add(log)
                    }
                    //Error while connecting to REST API
                } else {
                    when (logsService.httpStatusCode) {
                        404 -> Log.e("Rest Connection", "404 Not Found")
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

    @Preview(showBackground = true)
    @Composable
    fun LogsPreview() {

/*
        val logList = listOf(
            ImageLog( LocalDateTime.of(LocalDate.of(2023,3,25), LocalTime.NOON), 0),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,3,25), LocalTime.MIDNIGHT), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,3,25), LocalTime.NOON), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,3,26), LocalTime.NOON), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,3,26),LocalTime.MIDNIGHT), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,4,26),LocalTime.MIDNIGHT), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,4,26), LocalTime.NOON), 2),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,4,27), LocalTime.NOON), 0),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,5,28),LocalTime.MIDNIGHT), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,5,28), LocalTime.MIDNIGHT),1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,6,29), LocalTime.NOON), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,6,29),LocalTime.MIDNIGHT), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,7,29), LocalTime.NOON), 1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,8,30),LocalTime.MIDNIGHT), 2),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,8,30), LocalTime.MIDNIGHT),1),
            ImageLog( LocalDateTime.of(LocalDate.of(2023,9,30), LocalTime.MIDNIGHT), 1),
        )
*/

        val logComposables = LogComposables()

        getLogs()

        RaspSPYTheme {
            Column(){
                logComposables.ScrollableLogs(logList)
            }
        }
    }
}
