package de.hhn.softwarelab.raspy.ui.ImageLogsUI

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

    // Create a mutable list to hold ImageLog objects
    var logList = mutableListOf<ImageLog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val logComposables = ImageComposables()

        setContent {
            val logComposables = ImageComposables()
            val service = ImageLogService()
            var body by remember {
                mutableStateOf(emptyList<ImageLog>())
            }

            // Launch a coroutine to retrieve image logs from a service
            LaunchedEffect(Unit) {
                service.getImages()

                // Wait until the service's body property is not null
                while (service.getBody == null) {
                    delay(100)
                }

                // Assign the service's body to the 'body' variable
                body = service.getBody!!
            }

            // Create a mutable state for the dark mode flag
            val darkMode = remember { mutableStateOf(false) }

            // Set the content of the activity using Jetpack Compose
            RaspSPYTheme(
                darkTheme = darkMode
            ) {
                Column() {
                    // Display the scrollable logs using the provided composable function
                    logComposables.ScrollableLogs(body)
                }
            }
        }
    }

    // Preview function for Composable UI preview
    @Preview(showBackground = true)
    @Composable
    fun LogsPreview() {
        val logComposables = ImageComposables()
        val service = ImageLogService()
        var body by remember {
            mutableStateOf(emptyList<ImageLog>())
        }

        // Launch a coroutine to retrieve image logs from a service
        LaunchedEffect(Unit) {
            service.getImages()

            // Wait until the service's body property is not null
            while (service.getBody == null) {
                delay(100)
            }

            // Assign the service's body to the 'body' variable
            body = service.getBody!!
        }

        // Create a mutable state for the dark mode flag
        val darkMode = remember { mutableStateOf(false) }

        // Set the content of the preview using Jetpack Compose
        RaspSPYTheme(darkTheme = darkMode) {
            Column() {
                // Display the scrollable logs using the provided composable function
                logComposables.ScrollableLogs(body)
            }
        }
    }
}
