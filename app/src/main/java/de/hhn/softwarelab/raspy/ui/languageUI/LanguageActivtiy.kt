package de.hhn.softwarelab.raspy.ui.languageUI

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.ui.settings.SettingUI
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.delay

class LanguageActivity : ComponentActivity() {
    private var selectedLanguage: String = ""

    @OptIn(ExperimentalMaterial3Api::class)
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

            RaspSPYTheme(darkTheme = SettingUI.currentDarkModeState.value) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { androidx.compose.material3.Text(text = getString(R.string.info_title)) },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onBackPressed()
                                }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                                }
                            },
                            backgroundColor = if (SettingUI.currentDarkModeState.value) Color.Gray else Color.White
                        )
                    },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(
                                    if (SettingUI.currentDarkModeState.value) Color.Gray else Color(
                                        0xFFd2bfd6
                                    )
                                )
                        ) {
                            LanguageSelectionScreen(::switchLocale)
                        }
                    }
                )
            }
        }
    }

    private fun switchLocale(languageCode: String) {
        selectedLanguage = languageCode
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(java.util.Locale(languageCode))
        createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        recreate()
    }
}

val customTypography = Typography(
    h6 = TextStyle(fontSize = 24.sp, fontWeight = Bold)
)

@Composable
fun LanguageSelectionScreen(switchLocale: (String) -> Unit) {
    val supportedLanguages = listOf("English", "German","Spanish")
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { expanded = !expanded } // Toggle the expanded state on click
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.language_selection_text),
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.width(50.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(vertical = 8.dp)
            ) {

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = selectedLanguage,
                    style = customTypography.h6,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
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
                            }
                            expanded = false
                        }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = languageFlags[language] ?: 0),
                                contentDescription = "Language Flag",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = language,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
