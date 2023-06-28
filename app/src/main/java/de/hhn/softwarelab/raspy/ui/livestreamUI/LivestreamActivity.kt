@file:Suppress("DEPRECATION")

package de.hhn.softwarelab.raspy.ui.livestreamUI

import AuthenticationScreen
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.hhn.softwarelab.raspy.ui.livestreamUI.components.AppBar
import de.hhn.softwarelab.raspy.notification.PushNotificationService
import de.hhn.softwarelab.raspy.policy.PrivacyPolicy
import de.hhn.softwarelab.raspy.policy.PrivacyPolicyScreen
import de.hhn.softwarelab.raspy.ui.loginUI.components.FormType
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class LivestreamActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onStart() {
        super.onStart()
        setContent {
            if (!PrivacyPolicy.policyAccept.value) {
                PrivacyPolicyScreen { policyAccepted ->
                    if (policyAccepted) {
                        PrivacyPolicy.policyAccept.value = true
                        PushNotificationService.subscribePushNotifications("log", applicationContext)
                        setContent {
                            RaspSPYTheme(
                                darkTheme = SettingUI.currentDarkModeState.value
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
                    } else {
                        Toast.makeText(
                            this,
                            "You Cannot use App if you don´t accept our policy, Sorry!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                PushNotificationService.subscribePushNotifications("log", applicationContext)
                setContent {
                    RaspSPYTheme(
                        darkTheme = SettingUI.currentDarkModeState.value
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
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PushNotificationService.subscribePushNotifications("log", applicationContext)
        setContent {
            RaspSPYTheme(
                darkTheme = SettingUI.currentDarkModeState.value
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


