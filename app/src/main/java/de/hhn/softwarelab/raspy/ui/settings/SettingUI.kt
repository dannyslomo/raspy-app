package de.hhn.softwarelab.raspy.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.ui.livestreamUI.LivestreamActivity
import de.hhn.softwarelab.raspy.ui.settings.SettingUI.PreferenceState.isDarkMode
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.delay


class SettingUI : ComponentActivity() {
    object PreferenceState {
        var isDarkMode = mutableStateOf(false)
        var email  = mutableStateOf("John123@Gmail.com")
        var username  = mutableStateOf("John")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val settingService = SettingsService()
            var body by remember {
                mutableStateOf(emptyList<Settings>())
            }
            //getting Backend values
            LaunchedEffect(Unit) {
                settingService.getSettings()
                while (settingService.getBody == null) {
                    delay(100)
                }
                body = settingService.getBody!!
            }

            RaspSPYTheme(darkTheme = isDarkMode.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                )
                {
                    SettingsScreen(this, isDarkMode, body)
                }
            }
        }
    }
}


/**
 * SettingActivity Content
 * @param context
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(context: Context, darkMode: MutableState<Boolean>, body: List<Settings>) {
    val settingService = SettingsService()
    var settingID = globalValues.settingsId

    var currentDeleteInterval = remember { mutableStateOf(0) }
    var currentCameraActive = remember { mutableStateOf(false) }
    var currentSystemActive = remember { mutableStateOf(false) }

    body.forEach {
        currentDeleteInterval.value = it.deleteInterval!!
        currentSystemActive.value = it.systemActive!!
        currentCameraActive.value = it.cameraActive!!

        println(currentDeleteInterval)
        println(currentSystemActive)
        println(currentCameraActive)
    }


    var isSwitchEnabled1 by remember { mutableStateOf(currentCameraActive) }
    var isSwitchEnabled2 by remember { mutableStateOf(currentSystemActive) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed(context) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = if (darkMode.value) Color.Gray else Color.White
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
            ) {
                // Profile
                Profile(darkMode.value)
                //Activate/Deactivate System with SWITCH
                CardWithSwitch(
                    icon = R.drawable.camera_ras,
                    mainText = "Security System ",
                    switchState = isSwitchEnabled1.value,
                    onSwitchStateChanged = { isEnabled ->
                        isSwitchEnabled1.value = isEnabled
                        if (isEnabled) {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    true,
                                    currentCameraActive.value
                                ), settingID
                            )
                            currentCameraActive.value = true

                        } else {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    false,
                                    currentCameraActive.value
                                ), settingID
                            )
                            currentCameraActive.value = false
                            Toast.makeText(context, "1 OFF", Toast.LENGTH_SHORT).show()
                        }
                    },
                    darkMode = isDarkMode.value,
                    "Deactivate face/object detection AND Push-Notifications. \n(Camera is still on)"
                )
                //Activate/Deactivate Camera with SWITCH
                CardWithSwitch(
                    icon = R.drawable.camera_ras,
                    mainText = "Camera",
                    switchState = isSwitchEnabled2.value,
                    onSwitchStateChanged = { isEnabled ->
                        isSwitchEnabled2.value = isEnabled
                        if (isEnabled) {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    currentSystemActive.value,
                                    true
                                ), settingID
                            )
                            currentSystemActive.value = true
                            Toast.makeText(context, "2 ON", Toast.LENGTH_SHORT).show()
                        } else {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    currentSystemActive.value,
                                    false
                                ), settingID
                            )
                            currentSystemActive.value = false
                            Toast.makeText(context, "2 OFF", Toast.LENGTH_SHORT).show()
                        }
                    },
                    darkMode = isDarkMode.value, "deactivate Camera"
                )
                CardWithSwitch(
                    icon = if (isDarkMode.value)  R.drawable.darkmode else R.drawable.lightmode,
                    mainText = if (isDarkMode.value) "Dark Mode" else "Light Mode",
                    switchState = isDarkMode.value,
                    onSwitchStateChanged = {isEnabled ->
                        isDarkMode.value = isEnabled
                    },
                    darkMode = isDarkMode.value,
                    infoNote = ""
                )

                NumberPicker(darkMode.value, currentDeleteInterval, onSave = { newNumber ->
                    var savedNumber = newNumber
                    settingService.putSettings(
                        Settings(
                            savedNumber,
                            currentSystemActive.value, currentCameraActive.value
                        ), settingID
                    )
                })
            }
        }
    )
}


/**
 * press to go back to LiveStreamActivity
 */
private fun onBackPressed(context: Context) {
    val intent = Intent(context, LivestreamActivity::class.java)
    context.startActivity(intent)
    (context as? Activity)?.finish()
}

