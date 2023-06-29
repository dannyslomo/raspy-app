package de.hhn.softwarelab.raspy.livestreamUI

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import java.time.LocalDateTime
import de.hhn.softwarelab.raspy.ui.livestreamUI.components.MediaScreen


@Composable
fun ElevatedButtons(imageVector: ImageVector, contentDescriptor: String, onClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .padding(15.dp)
            .size(size = 100.dp),
        onClick = onClick
    )
    {
        Icon(
            modifier = Modifier.size(size = 100.dp),
            imageVector = imageVector,
            contentDescription = contentDescriptor
        )
    }
}


@Composable
fun InitButton() {
    val contextForToast = LocalContext.current.applicationContext
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MediaScreen()
        }
        Spacer(modifier = Modifier.height(160.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButtons(
                imageVector = Icons.Default.CameraAlt,
                "Save picture",
                onClick = {
                    val imageLogService = ImageLogService()
                    imageLogService.postImage(ImageLog(LocalDateTime.now(), 0, null))
                    Toast.makeText(contextForToast, "Picture saved!", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.width(30.dp))
            ElevatedButtons(
                onClick = {
                    Toast.makeText(contextForToast, "Not working at the moment coming soon!", Toast.LENGTH_SHORT).show()
                },
                imageVector = Icons.Default.KeyboardVoice,
                contentDescriptor = "Activate voice"
            )
        }
    }
}

