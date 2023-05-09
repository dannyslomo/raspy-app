package de.hhn.softwarelab.raspy.ImageLogsUI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
//import coil.compose.rememberAsyncImagePainter
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.*

class ImageActivity : ComponentActivity() {

    var logList = mutableListOf<ImageLog>()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val logComposables = ImageComposables()

        setContent {
            val darkMode = remember { mutableStateOf(false) }
            RaspSPYTheme(
                darkTheme = darkMode
            ) {
                Column(){
                    logComposables.ScrollableLogs(logList)
                }
            }
        }
    }

    fun getLogs() : List<ImageLog>{
        val service = ImageLogService()
        // launch a coroutine to run the getImages() function
        val job = GlobalScope.launch {
            service.getImages()
        }

        // wait for the coroutine to complete before continuing
        runBlocking {
            job.join()
        }
        return service.getBody!!
    }


    @Preview(showBackground = true)
    @Composable
    fun LogsPreview() {
        val logComposables = ImageComposables()
        val service = ImageLogService()
        var body by remember {
            mutableStateOf(emptyList<ImageLog>())
        }

        LaunchedEffect(Unit) {
            service.getImages()
            while (service.getBody == null) {
                delay(100)
            }
            body = service.getBody!!
        }
        val darkMode = remember { mutableStateOf(false) }
        RaspSPYTheme(
            darkTheme = darkMode
        ) {
            Column() {
                logComposables.ScrollableLogs(body)
            }
        }
    }
}
