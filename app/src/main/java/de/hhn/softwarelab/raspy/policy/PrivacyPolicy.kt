package de.hhn.softwarelab.raspy.policy

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
import kotlinx.coroutines.delay

class PrivacyPolicy {
    companion object {
        var policyAccept = mutableStateOf(false)
    }
}

@Composable
fun PrivacyPolicyScreen(onPolicyAccepted: (Boolean) -> Unit) {
    val settingService = SettingsService()
    var body by remember {
        mutableStateOf(emptyList<Settings>())
    }
    // Getting Backend values
    LaunchedEffect(Unit) {
        settingService.getSettings()
        while (settingService.getBody == null) {
            Log.i("PrivacyPolicy", "Waiting for server...")
            delay(500)
        }
        body = settingService.getBody!!
    }

    var currentDeleteInterval = remember { mutableStateOf(0) }
    var currentCameraActive = remember { mutableStateOf(false) }
    var currentSystemActive = remember { mutableStateOf(false) }
    var currentLanguageState = remember { mutableStateOf("en") }
    var currentPolicyState = remember { mutableStateOf(false) }
    var settingID = globalValues.settingsId

    body.forEach {
        currentDeleteInterval.value = it.deleteInterval!!
        currentSystemActive.value = it.systemActive!!
        currentCameraActive.value = it.cameraActive!!
        SettingUI.currentDarkModeState.value = it.darkMode!!
        currentLanguageState.value = it.language!!
        currentPolicyState.value = it.policy!!
    }

    if (!currentPolicyState.value) {
        val scrollState = rememberScrollState()

        Card(modifier = Modifier.padding(30.dp), shape = RoundedCornerShape(30.dp)) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.verticalScroll(scrollState)) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .padding(bottom = 60.dp)
                    ) {
                        Text(
                            text = "Datenschutzerklärung und Nutzungsbedingungen",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Vielen Dank, dass Sie unsere App nutzen möchten. Bevor Sie die App verwenden können, müssen Sie unsere Datenschutzerklärung und Nutzungsbedingungen akzeptieren. Diese legen fest, wie wir mit Ihren Daten umgehen und welche Regeln für die Nutzung der App gelten. Bitte lesen Sie die folgenden Informationen sorgfältig durch.\n" +
                                    "\n" + "\n" +
                                    "Datenschutzerklärung:\n" +
                                    "Unsere Datenschutzerklärung beschreibt, welche Daten wir von Ihnen erfassen, wie wir sie verwenden, speichern und schützen. Sie enthält auch Informationen zu Ihren Rechten in Bezug auf Ihre persönlichen Daten. Indem Sie diese App nutzen, stimmen Sie unserer Datenschutzerklärung zu.\n" +
                                    "\n" +
                                    "Nutzungsbedingungen:\n" +
                                    "Unsere Nutzungsbedingungen legen fest, wie Sie die App verwenden dürfen und welche Verhaltensregeln Sie einhalten müssen. Darin sind auch Haftungsausschlüsse und rechtliche Bestimmungen enthalten. Es ist wichtig, dass Sie diese Bedingungen verstehen und akzeptieren, bevor Sie die App nutzen.\n" +
                                    "\n" +
                                    "Zustimmung erforderlich:\n" +
                                    "Bitte beachten Sie, dass die Nutzung unserer App Ihre Zustimmung zu unseren Datenschutzbestimmungen und Nutzungsbedingungen erfordert. Wenn Sie mit diesen Bedingungen nicht einverstanden sind, können Sie die App nicht verwenden. Durch Klicken auf \"Akzeptieren\" oder die Fortsetzung der Nutzung der App erklären Sie, dass Sie unsere Datenschutzerklärung und Nutzungsbedingungen gelesen haben und ihnen zustimmen.\n" +
                                    "\n" +
                                    "Kontakt:\n" +
                                    "Wenn Sie Fragen oder Bedenken bezüglich unserer Datenschutzerklärung oder Nutzungsbedingungen haben, können Sie sich jederzeit an uns wenden. Wir stehen Ihnen gerne zur Verfügung, um Ihre Fragen zu beantworten.\n" +
                                    "\n" +
                                    "Bitte beachten Sie, dass diese Informationen allgemeiner Natur sind und nicht als rechtliche Beratung dienen. Es wird empfohlen, sich bei Bedarf rechtlich beraten zu lassen, um sicherzustellen, dass Ihre Datenschutzerklärung und Nutzungsbedingungen den geltenden Gesetzen und Bestimmungen entsprechen.",
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

                if (scrollState.value == scrollState.maxValue) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(onClick = {
                                onPolicyAccepted(true) // Call the callback with "true" when "Accept" is clicked
                                settingService.putSettings(
                                    Settings(
                                        currentDeleteInterval.value,
                                        currentSystemActive.value,
                                        currentCameraActive.value,
                                        SettingUI.currentDarkModeState.value,
                                        currentLanguageState.value,
                                        true
                                    ), settingID
                                )
                            }) {
                                Text("Accept")
                            }
                            Button(onClick = {
                                onPolicyAccepted(false) // Call the callback with "false" when "Decline" is clicked
                            }) {
                                Text("Decline")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowAlertDialog() {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Alert") },
            text = { Text(text = "This is an alert dialog.") },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text(text = "OK")
                }
            }
        )
    }
}



