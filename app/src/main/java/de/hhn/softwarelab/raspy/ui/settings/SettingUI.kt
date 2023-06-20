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
import androidx.compose.ui.res.stringResource
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.ui.livestreamUI.LivestreamActivity
import de.hhn.softwarelab.raspy.ui.settings.SettingUI.Companion.currentDarkModeState
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.delay


class SettingUI : ComponentActivity() {
    companion object {
        var currentDarkModeState = mutableStateOf(false)
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

            RaspSPYTheme(darkTheme = currentDarkModeState.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                )
                {
                    SettingsScreen(this, body)
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
fun SettingsScreen(context: Context, body: List<Settings>) {
    val settingService = SettingsService()
    var settingID = globalValues.settingsId

    var currentDeleteInterval = remember { mutableStateOf(0) }
    var currentCameraActive = remember { mutableStateOf(false) }
    var currentSystemActive = remember { mutableStateOf(false) }
    var currentLanguageState = remember { mutableStateOf("en") }
    var currentPolicyState = remember { mutableStateOf(false) }

    body.forEach {
        currentDeleteInterval.value = it.deleteInterval!!
        currentSystemActive.value = it.systemActive!!
        currentCameraActive.value = it.cameraActive!!
        currentDarkModeState.value = it.darkMode!!
        currentLanguageState.value = it.language!!
        currentPolicyState.value = it.policy!!

        println(currentDeleteInterval)
        println(currentSystemActive)
        println(currentCameraActive)
        println(currentDarkModeState)
        println(currentLanguageState)
        println(currentPolicyState)
    }


    val isSwitchEnabled1 by remember { mutableStateOf(currentCameraActive) }
    val isSwitchEnabled2 by remember { mutableStateOf(currentSystemActive) }
    val isSwitchEnabled3 by remember { mutableStateOf(currentDarkModeState) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed(context) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = if (currentDarkModeState.value) Color.Gray else Color.White
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
            ) {
                // Profile
                Profile(currentDarkModeState.value)
                //Activate/Deactivate System with SWITCH
                CardWithSwitch(
                    icon = R.drawable.camera_ras,
                    mainText = stringResource(R.string.security_system),
                    switchState = isSwitchEnabled1.value,
                    onSwitchStateChanged = { isEnabled ->
                        isSwitchEnabled1.value = isEnabled
                        if (isEnabled) {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    true,
                                    currentCameraActive.value,
                                    currentDarkModeState.value,
                                    currentLanguageState.value,
                                    currentPolicyState.value
                                ), settingID
                            )
                            currentCameraActive.value = true

                        } else {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    false,
                                    currentCameraActive.value,
                                    currentDarkModeState.value,
                                    currentLanguageState.value,
                                    currentPolicyState.value
                                ), settingID
                            )
                            currentCameraActive.value = false
                            Toast.makeText(context, "1 OFF", Toast.LENGTH_SHORT).show()
                        }
                    },
                    darkMode = currentDarkModeState.value,
                    "Deactivate face/object detection AND Push-Notifications. \n(Camera is still on)"
                )
                //Activate/Deactivate Camera with SWITCH
                CardWithSwitch(
                    icon = R.drawable.camera_ras,
                    mainText = stringResource(R.string.camera),
                    switchState = isSwitchEnabled2.value,
                    onSwitchStateChanged = { isEnabled ->
                        isSwitchEnabled2.value = isEnabled
                        if (isEnabled) {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    currentSystemActive.value,
                                    true,
                                    currentDarkModeState.value,
                                    currentLanguageState.value,
                                    currentPolicyState.value
                                ), settingID
                            )
                            currentSystemActive.value = true
                            Toast.makeText(context, "2 ON", Toast.LENGTH_SHORT).show()
                        } else {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval.value,
                                    currentSystemActive.value,
                                    false,
                                    currentDarkModeState.value,
                                    currentLanguageState.value,
                                    currentPolicyState.value
                                ), settingID
                            )
                            currentSystemActive.value = false
                            Toast.makeText(context, "2 OFF", Toast.LENGTH_SHORT).show()
                        }
                    },
                    darkMode = currentDarkModeState.value, stringResource(R.string.deactivate_camera)
                )
                CardWithSwitch(
                    icon = if (currentDarkModeState.value) R.drawable.darkmode else R.drawable.lightmode,
                    mainText = if (currentDarkModeState.value) "Dark Mode" else "Light Mode",
                    switchState = isSwitchEnabled3.value,
                    onSwitchStateChanged = { isEnabled ->
                        currentDarkModeState.value = isEnabled
                    },
                    darkMode = currentDarkModeState.value,
                    infoNote = stringResource(id = R.string.darkmode_info_note)
                )

                NumberPicker(currentDarkModeState.value, currentDeleteInterval, onSave = { newNumber ->
                    settingService.putSettings(
                        Settings(
                            newNumber,
                            currentSystemActive.value,
                            currentCameraActive.value,
                            true,
                            currentLanguageState.value,
                            currentPolicyState.value
                        ), settingID
                    )
                    currentDarkModeState.value = true
                })
            }
        }
    )
}


/**
 * press to go back to LiveStreamActivity
 */
fun onBackPressed(context: Context) {
    val intent = Intent(context, LivestreamActivity::class.java)
    context.startActivity(intent)
    (context as? Activity)?.finish()
}

