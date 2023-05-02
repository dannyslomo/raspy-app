package de.hhn.softwarelab.raspy.ImageLogsUI


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RingVolume
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*


class LogComposables {

    @Composable
    fun ScrollableLogs(logs: List<ImageLog>) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (log in logs) {
                LogBox(
                    text = log.timeStamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy/ HH:mm:ss")),
                    detectionType = log.triggerType
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    // 0 = User, 1 = Automatisch, 2 = Klingel
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LogBox(text: String, detectionType: Int, onClick: () -> Unit = {}) {
        Card(
            shape = MaterialTheme.shapes.extraLarge,
            onClick = onClick,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    when (detectionType) {
                        0 -> {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = "Person",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        1 -> {
                            Icon(
                                Icons.Outlined.Camera,
                                contentDescription = "Camera",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        2 -> {
                            Icon(
                                Icons.Outlined.RingVolume,
                                contentDescription = "Ring",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                    Text(
                        text = text,
                        fontSize = 29.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
