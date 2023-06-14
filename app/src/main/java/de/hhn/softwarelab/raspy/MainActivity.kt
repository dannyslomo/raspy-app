package de.hhn.softwarelab.raspy


import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.Services.UserService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.backend.dataclasses.User
import de.hhn.softwarelab.raspy.ui.settings.SettingUI.PreferenceState.isDarkMode
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.util.*



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspSPYTheme(darkTheme = isDarkMode.value) {
            }
        }
    }

    @Composable
    fun StandardButton(
        text: String,
        onClick: () -> Unit = {}
    ) {
        Button(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            onClick = onClick
        ) {
            Text(text = text)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SettingsPreview() {
        val imageLogService = ImageLogService()
        val settingsService = SettingsService()
        val userService = UserService()
        val darkMode = remember { mutableStateOf(false)}
        RaspSPYTheme(darkTheme = isDarkMode.value) {
            Column {
                StandardButton(text = "Get Settings", onClick = { settingsService.getSettings() })
                //StandardButton(text = "Post Settings", onClick = { settingsService.postSettings(Settings(5, true, true, null)) })
                //StandardButton(text = "Put Settings" , onClick = { settingsService.putSettings(Settings(12, false, true),32)})
                StandardButton(text = "Get Logs", onClick = { imageLogService.getImages() })
                StandardButton(text = "Post Log", onClick = { imageLogService.postImage(ImageLog(LocalDateTime.now(), 0, null)) })
                //StandardButton(text = "delete Log", onClick = { imageLogService.deleteImage( 27) })

                StandardButton(text = "Register", onClick = {userService.register(User("max", "muster", "maxmu19", "123456", "maxmu2@test.de", null))})
                StandardButton(text = "Login", onClick = {userService.login(User("", "", "maxmu18", "123456", "", null))})
            }
        }
    }
}