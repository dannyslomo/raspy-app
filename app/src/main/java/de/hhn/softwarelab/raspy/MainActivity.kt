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
import com.google.android.exoplayer2.util.Log
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.Services.SettingsService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.backend.dataclasses.Settings
import de.hhn.softwarelab.raspy.notification.NotificationUtils
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme
import kotlinx.coroutines.*
import java.net.ConnectException
import java.time.LocalDateTime
import java.util.*



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspSPYTheme {
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
        RaspSPYTheme {
            Column {
                StandardButton(text = "Get Settings", onClick = { settingsService.getSettings() })
                StandardButton(text = "Post Settings", onClick = { settingsService.postSettings(Settings(5, true, true)) })
                StandardButton(text = "Put Settings" , onClick = { settingsService.putSettings(Settings(5, true, true),4)})
                StandardButton(text = "Get Logs", onClick = { imageLogService.getLogs() })
                StandardButton(text = "Post Log", onClick = { imageLogService.postLog(ImageLog(LocalDateTime.now(), 2)) })
                StandardButton(text = "Put Log", onClick = { imageLogService.putLog(ImageLog(LocalDateTime.now(), 2), 2) })
            }
        }
    }
}





