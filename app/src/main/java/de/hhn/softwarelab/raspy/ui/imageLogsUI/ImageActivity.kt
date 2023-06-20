package de.hhn.softwarelab.raspy.ui.ImageLogsUI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
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
            var isLoading by remember {
                mutableStateOf(true)
            }
            var isConnected by remember {
                mutableStateOf(true)
            }

            // Launch a coroutine to retrieve image logs from a service
            LaunchedEffect(Unit) {

                service.getImages { connected ->
                    isConnected = connected
                }
                // Wait until the service's body property is not null
                while (service.getBody == null) {
                    delay(100)
                }

                // Assign the service's body to the 'body' variable
                body = service.getBody!!

                // Set isLoading to false once the body is retrieved
                isLoading = false
            }

            // Create a mutable state for the dark mode flag
            val darkMode = remember { mutableStateOf(false) }

            // Set the content of the activity using Jetpack Compose
            RaspSPYTheme(
                darkTheme = darkMode.value
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (isLoading) {
                        // Display the loading animation while waiting for data
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(120.dp)
                                .padding(16.dp),
                            color = Color.Green
                        )
                        if(!isConnected){
                            // Display the loading animation while waiting for data
                            Text(
                                text = "No Connection",
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(16.dp),
                                color = Color.Red
                            )
                        }
                    } else {
                        // Display the scrollable logs using the provided composable function
                        logComposables.ScrollableLogs(body)
                    }
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
        var isConnected by remember {
            mutableStateOf(true)
        }

        // Launch a coroutine to retrieve image logs from a service
        LaunchedEffect(Unit) {
            service.getImages { connected ->
                isConnected = connected
            }

            // Wait until the service's body property is not null
            while (service.getBody == null) {
                delay(100)
            }

            // Assign the service's body to the 'body' variable
            body = service.getBody!!
        }

        RaspSPYTheme(darkTheme = SettingUI.PreferenceState.isDarkMode.value) {
            Column() {
                // Display the scrollable logs using the provided composable function
                logComposables.ScrollableLogs(body)
            }
        }
    }
}
