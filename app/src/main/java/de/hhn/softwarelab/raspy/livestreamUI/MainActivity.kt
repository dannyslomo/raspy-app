@file:Suppress("DEPRECATION")

package de.hhn.softwarelab.raspy.livestreamUI

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
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Log.LOG_LEVEL_OFF
import de.hhn.softwarelab.raspy.notifications.PushNotificationService
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushNotificationService.subscribePushNotifications("log",applicationContext)
        Log.setLogLevel(LOG_LEVEL_OFF)
        setContent {
            RaspSPYTheme(darkTheme = false) {
                 Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            AppBar()
                        },
                        content = { padding ->
                            Column(modifier = Modifier
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


