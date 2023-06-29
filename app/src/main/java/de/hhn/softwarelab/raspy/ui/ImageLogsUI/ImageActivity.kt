package de.hhn.softwarelab.raspy.ui.ImageLogsUI

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.ui.livestreamUI.LivestreamActivity
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
//import coil.compose.rememberAsyncImagePainter
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.*

class ImageActivity : ComponentActivity() {

    // Create a mutable list to hold ImageLog objects
    var logList = mutableListOf<ImageLog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                darkTheme = SettingUI.currentDarkModeState.value
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(R.string.gallery)) },
                            navigationIcon = {
                                IconButton(onClick = { onBackPressed(this) }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                }
                            },
                            backgroundColor = if (SettingUI.currentDarkModeState.value) Color.Gray else Color.White
                        )
                    },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier.padding(paddingValues),
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
                                    if (!isConnected) {
                                        // Display error text if no connection
                                        Text(
                                            text = getString(R.string.no_connection),
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
                )
            }
        }
    }

    private fun onBackPressed(context: Context) {
        val intent = Intent(context, LivestreamActivity::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }
}
