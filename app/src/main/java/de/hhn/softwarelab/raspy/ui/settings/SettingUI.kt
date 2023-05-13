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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import de.hhn.softwarelab.raspy.livestreamUI.LivestreamActivity
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.delay

class SettingUI : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val darkMode = remember { mutableStateOf(false) }
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

            RaspSPYTheme(darkTheme = darkMode) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                )
                {
                    SettingsScreen(this, darkMode, body)
                }
            }
        }
    }
}


/**
 * SettingActivity Content
 * @param context
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(context: Context, darkMode: MutableState<Boolean>, body: List<Settings>) {
    val settingService = SettingsService()
    val settingID = 1
    //values
    var currentDeleteInterval = 0
    var currentCameraActive = true
    var currentSystemActive = true
    //update backend values
    body.forEach {
        currentDeleteInterval = it.deleteInterval!!
        currentSystemActive = it.systemActive!!
        currentCameraActive = it.cameraActive!!

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
                /**
                 * User-Profile
                 */
                Profile(darkMode.value)
                /**
                 * Switch: Activate/Deactivate System
                 */
                CardWithSwitch(
                    icon = R.drawable.user_profil_icon,
                    mainText = "Security System ",
                    switchState = isSwitchEnabled1,
                    onSwitchStateChanged = { isEnabled ->
                        isSwitchEnabled1 = isEnabled
                        if (isEnabled) {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval,
                                    true,
                                    currentCameraActive
                                ), settingID
                            )
                            currentSystemActive = true
                            Toast.makeText(context, "1 ON", Toast.LENGTH_SHORT).show()
                        } else {
                            //TODO deleteInterval und cameraActive gleich lassen
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval,
                                    false,
                                    currentCameraActive
                                ), settingID
                            )
                            currentCameraActive = false
                            Toast.makeText(context, "1 OFF", Toast.LENGTH_SHORT).show()
                        }
                    },
                    darkMode = darkMode.value
                )
                //Activate/Deactivate Camera with SWITCH
                CardWithSwitch(
                    icon = R.drawable.user_profil_icon,
                    mainText = "Camera",
                    switchState = isSwitchEnabled2,
                    onSwitchStateChanged = { isEnabled ->
                        isSwitchEnabled2 = isEnabled
                        if (isEnabled) {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval,
                                    currentSystemActive,
                                    true
                                ), settingID
                            )
                            currentCameraActive = true
                            Toast.makeText(context, "2 ON", Toast.LENGTH_SHORT).show()
                        } else {
                            settingService.putSettings(
                                Settings(
                                    currentDeleteInterval,
                                    currentSystemActive,
                                    false
                                ), settingID
                            )
                            currentSystemActive = false
                            Toast.makeText(context, "2 OFF", Toast.LENGTH_SHORT).show()
                        }
                    },
                    darkMode = darkMode.value
                )
                CardWithSwitch(
                    icon = R.drawable.user_profil_icon,
                    mainText = "Dark Mode",
                    switchState = darkMode.value,
                    onSwitchStateChanged = { isEnabled ->
                        darkMode.value = isEnabled
                        if (isEnabled) {
                            darkMode.value = true
                            checkDarkMode(true)
                        } else {
                            // Disable dark mode
                            darkMode.value = false
                            checkDarkMode(false)
                        }
                    }, darkMode = darkMode.value
                )
                NumberPicker(darkMode.value, currentDeleteInterval, onSave = {settingService.putSettings(
                    Settings(
                        currentDeleteInterval,
                        currentSystemActive,
                        false
                    ), settingID
                ) })
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

private fun checkDarkMode(darkMode: Boolean): Boolean {
    return darkMode
}

//TODO: fix Compose Version -> material3 and material error
/**
 * Update UI when scrolling up
 */
@Composable
fun ScrollableUI() {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) { index ->
            Text("Item $index")
        }
    }
    // Update the UI when the user scrolls up
    if (scrollState.firstVisibleItemIndex == 0) {
        // Add your UI update logic here
    }
}