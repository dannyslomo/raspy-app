package de.hhn.softwarelab.raspy.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
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
import androidx.core.app.ActivityCompat
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.policy.PrivacyPolicy.Companion.currentPolicyState
import de.hhn.softwarelab.raspy.ui.livestreamUI.LivestreamActivity
import de.hhn.softwarelab.raspy.ui.settings.SettingUI.Companion.currentLanguageState
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.delay

/**
 * The SettingUI class extends ComponentActivity and represents the main activity responsible for managing the settings screen.
 * It utilizes components from the SettingComponents class and arranges them in the correct order to display and access their functions.
 */

class SettingUI : ComponentActivity() {
    /**
     * Companion object containing static variables related to dark mode state and selected language.
     */
    companion object {
        var currentDarkModeState = mutableStateOf(false)
        var currentCameraActive = mutableStateOf(false)
        var currentSystemActive = mutableStateOf(false)
        val currentLanguageState =  mutableStateOf("en")
    }

    /**
     * Overrides the onCreate method of the activity, initializing the UI and setting up the settings screen.
     * @param savedInstanceState The saved instance state bundle.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if(currentLanguageState.value != "en"){
            switchLocale(currentLanguageState.value)
        }
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
                    SettingsScreen(body)
                }
            }
        }
    }

    /**
     *
     * Composable function for the Settings screen.
     * This function is responsible for displaying the Settings screen UI.
     * The settings are used to initialize the state variables for various settings options.
     * The function includes UI components such as language selection,
     * card with switch for security system activation, card with switch for camera activation, card
     * with switch for dark mode, and a number picker for delete interval.
     * @param body The list of settings to initialize the UI state variables.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SettingsScreen(body: List<Settings>) {
        val settingService = SettingsService()
        val settingID = globalValues.settingsId

        val currentDeleteInterval = remember { mutableStateOf(0) }
        val currentLanguageState = remember { mutableStateOf("en") }
        val dMode = remember { mutableStateOf(false) }

        body.forEach {
            currentDeleteInterval.value = it.deleteInterval!!
            currentSystemActive.value = it.systemActive!!
            currentCameraActive.value = it.cameraActive!!
            dMode.value = it.darkMode!!
            currentLanguageState.value = it.language!!
            currentPolicyState.value = it.policy!!
        }

        val isSwitchEnabled1 by remember { mutableStateOf(currentCameraActive) }
        val isSwitchEnabled2 by remember { mutableStateOf(currentSystemActive) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.settings)) },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed(this) }) {
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
                    //Change Language option with a combo box
                    LanguageSelectionScreen(::switchLocale, onItemClick =  {language ->
                        settingService.putSettings(
                            Settings(
                                currentDeleteInterval.value,
                                currentSystemActive.value,
                                currentCameraActive.value,
                                dMode.value,
                                language,
                                currentPolicyState.value
                            ), settingID
                        )
                    currentLanguageState.value = language})
                    //Option to turn on/off the security system with a switch
                    CardWithSwitch(
                        icon = R.drawable.system,
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
                                        dMode.value,
                                        currentLanguageState.value,
                                        currentPolicyState.value
                                    ), settingID
                                )
                                currentSystemActive.value = true

                            } else {
                                settingService.putSettings(
                                    Settings(
                                        currentDeleteInterval.value,
                                        false,
                                        currentCameraActive.value,
                                        dMode.value,
                                        currentLanguageState.value,
                                        currentPolicyState.value
                                    ), settingID
                                )
                                currentSystemActive.value = false
                            }
                        },
                        darkMode = currentDarkModeState.value,
                        "Deactivate face/object detection AND Push-Notifications. \n(Camera is still on)"
                    )
                    //Option to turn on/off the security system with a switch
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
                                        dMode.value,
                                        currentLanguageState.value,
                                        currentPolicyState.value
                                    ), settingID
                                )
                                currentCameraActive.value = true
                            } else {
                                settingService.putSettings(
                                    Settings(
                                        currentDeleteInterval.value,
                                        currentSystemActive.value,
                                        false,
                                        dMode.value,
                                        currentLanguageState.value,
                                        currentPolicyState.value
                                    ), settingID
                                )
                                currentCameraActive.value = false
                            }
                        },
                        darkMode = currentDarkModeState.value,
                        stringResource(R.string.deactivate_camera)
                    )
                    //Option to turn dark mode/light mode with a switch
                    CardWithSwitch(
                        icon = if (currentDarkModeState.value) R.drawable.darkmode else R.drawable.lightmode,
                        mainText = if (currentDarkModeState.value) stringResource(R.string.dark_mode) else stringResource(
                            R.string.light_mode
                        ),
                        switchState = currentDarkModeState.value,
                        onSwitchStateChanged = { isEnabled ->
                            currentDarkModeState.value = isEnabled
                        },
                        darkMode = currentDarkModeState.value,
                        infoNote = stringResource(id = R.string.darkmode_info_note)
                    )

                    NumberPicker(
                        currentDarkModeState.value,
                        currentDeleteInterval,
                        onSave = { newNumber ->
                            settingService.putSettings(
                                Settings(
                                    newNumber,
                                    currentSystemActive.value,
                                    currentCameraActive.value,
                                    dMode.value,
                                    currentLanguageState.value,
                                    currentPolicyState.value
                                ), settingID
                            )
                            currentDeleteInterval.value = newNumber
                        })
                }
            }
        )
    }

    /**
     * Switches the locale of the application to the specified language code.
     * @param languageCode The language code to switch to.
     **/
    fun switchLocale(languageCode: String): String {
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(java.util.Locale(languageCode))
        createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        ActivityCompat.recreate(this)
        return languageCode
    }

    /**
     * Handles the back button press event.
     * This method is called when the back button is pressed, and it starts the LivestreamActivity.
     * @param context The context from which the method is called.
     */
    private fun onBackPressed(context: Context) {
        val intent = Intent(context, LivestreamActivity::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }
}

