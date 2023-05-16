@file:Suppress("DEPRECATION")

package de.hhn.softwarelab.raspy.ui.livestreamUI

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import de.hhn.softwarelab.raspy.livestreamUI.AppBar
import de.hhn.softwarelab.raspy.notifications.PushNotificationService
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class LivestreamActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushNotificationService.subscribePushNotifications("log", applicationContext)
        setContent {
            val darkMode = remember { mutableStateOf(false) }

            //Für das Ausblenden der AppBar (wird später implementiert)
            /*
            val showTopBar = remember { mutableStateOf(true) }
            val isLandscape =
                LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
            if (isLandscape) {
                showTopBar.value = false
            }
             */

            RaspSPYTheme(
                darkTheme = darkMode
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBar()
                    },
                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .background(MaterialTheme.colorScheme.surface),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                        }
                    }
                )
            }
        }
    }
}


