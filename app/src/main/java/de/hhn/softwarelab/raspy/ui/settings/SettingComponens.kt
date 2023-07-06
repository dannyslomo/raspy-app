package de.hhn.softwarelab.raspy.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.ui.theme.Purple40


/**
 *
 * Composable function for a card with a switch.
 * This function displays a card with an icon, main text, and a switch.
 * The switch state can be toggled by the user, and a dialog with additional information can be shown.
 * @param icon The resource ID of the icon to display.
 * @param mainText The main text to display.
 * @param switchState The current state of the switch.
 * @param onSwitchStateChanged The callback function to be called when the switch state changes.
 * @param darkMode Indicates if the dark mode is enabled.
 * @param infoNote Additional information to display in the dialog.
 */
@Composable
fun CardWithSwitch(
    icon: Int,
    mainText: String,
    switchState: Boolean,
    onSwitchStateChanged: (Boolean) -> Unit,
    darkMode: Boolean,
    infoNote: String
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = ShapeDefaults.Large,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = ShapeDefaults.Medium)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .offset(y = (2).dp)
                        .width(110.dp)
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (darkMode) Color.White else Color.Black
                    )
                }
            }
            Switch(
                checked = switchState,
                onCheckedChange = onSwitchStateChanged,
                modifier = Modifier.padding(start = 110.dp)
            )
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info Icon"
                )
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = mainText) },
            text = { Text(text = infoNote) },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}


/**
 *
 * Composable function for the number picker.
 * This function displays a card with a number picker UI.
 * The user can increment or decrement the number to choose the
 * interval to delete the images in days
 * @param darkMode Indicates if the dark mode is enabled.
 * @param currentVal The current value of the number picker.
 * @param onSave The callback function to be called when the changes are saved.
 */
@Composable
fun NumberPicker(darkMode: Boolean, currentVal: MutableState<Int>, onSave: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(170.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = ShapeDefaults.Large,
    ) {
        var originalNumber by remember { mutableStateOf(currentVal.value) }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(18.dp)
                )
                Text(
                    text = stringResource(R.string.delete_interval),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkMode) Color.White else Color.Black,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { currentVal.value-- },
                    enabled = currentVal.value > 0,
                    modifier = Modifier.padding(end = 8.dp),
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.White) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    ),
                ) {
                    Text(text = "-", fontSize = 20.sp)
                }
                Text(
                    text = currentVal.value.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (darkMode) Color.White else Color.Black
                )
                TextButton(
                    onClick = { currentVal.value++ },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.White) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    ),
                ) {
                    Text(text = "+", fontSize = 20.sp)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        onSave(currentVal.value)
                        originalNumber = currentVal.value
                    },
                    modifier = Modifier.padding(top = 16.dp, end = 8.dp),
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.White) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    )
                ) {
                    Text(text = stringResource(R.string.save_button))
                }
                TextButton(
                    onClick = {
                        currentVal.value = originalNumber
                    },
                    colors = if (darkMode) ButtonDefaults.textButtonColors(contentColor = Color.Red) else ButtonDefaults.textButtonColors(
                        contentColor = Purple40
                    ),
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp)
                ) {
                    Text(text = stringResource(R.string.cancel_button))
                }
            }
        }
    }
}

/**
 *
 * Composable function that displays the language selection screen.
 * It allows the user to choose the language for the application.
 * The function utilizes a dropdown menu to show the available languages and their corresponding flags.
 * When a language is selected, the switchLocale function is called to switch the application's locale.
 * @param switchLocale Function to switch the application's locale based on the selected language code.
 * @param onItemClick A callback function to be called when a language is selected. It receives the language code as a parameter.
 */
@Composable
fun LanguageSelectionScreen(switchLocale: (String) -> Unit, onItemClick: (String) -> Unit) {
    val supportedLanguages = listOf("English", "German", "Spanish")
    val languageFlags = mapOf(
        "English" to R.drawable.english_flag,
        "German" to R.drawable.german_flag,
        "Spanish" to R.drawable.spanish_flag
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("") }

    // Retrieve the initially selected language
    if (selectedLanguage.isBlank()) {
        selectedLanguage = LocalConfiguration.current.locales[0].language
    }
    Card(
        elevation = 7.dp,
        shape = ShapeDefaults.Large,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = ShapeDefaults.Medium)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.language),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.language_selection_text),
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { expanded = !expanded }
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = selectedLanguage,
                        fontSize = 20.sp,
                    )
                }
                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {
                        supportedLanguages.forEach { language ->
                            val languageCode = when (language) {
                                "English" -> "en"
                                "German" -> "de"
                                "Spanish" -> "es"
                                else -> ""
                            }
                            DropdownMenuItem(
                                onClick = {
                                    selectedLanguage = language
                                    if (languageCode.isNotEmpty()) {
                                        switchLocale(languageCode)
                                        onItemClick(languageCode)
                                    }
                                    expanded = false
                                }
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(
                                            id = languageFlags[language] ?: 0
                                        ),
                                        contentDescription = "Language Flag",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = language,
                                        fontSize = 20.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

