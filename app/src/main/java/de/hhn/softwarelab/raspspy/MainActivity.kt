package de.hhn.softwarelab.raspspy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.hhn.softwarelab.raspspy.notification.NotificationUtils
import de.hhn.softwarelab.raspspy.ui.theme.RaspSPYTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Access the system service in onCreate()
        val notificationUtils = NotificationUtils()
        notificationUtils.checkNotificationPermission(this)
        notificationUtils.getNotification(1, this)

        setContent {
            RaspSPYTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(text = "hallo")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RaspSPYTheme {
        Greeting("Android")
    }
}