package de.hhn.softwarelab.raspspy.livestreamUI

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.tiles.material.Text

@Composable
fun ElevatedButtons(text: String, imageVector: ImageVector, contentDescriptor: String){
    val contextForToast = LocalContext.current.applicationContext
    ElevatedButton(
        modifier = Modifier
            .padding(15.dp)
            .size(size = 100.dp),
        onClick = {
            Toast.makeText(contextForToast, text, Toast.LENGTH_SHORT).show()
        }
    )
    {
        Icon(
            modifier = Modifier.size(size = 100.dp),
            imageVector = imageVector,
            contentDescription = contentDescriptor
        )
        Text("Test")
    }
}

@Composable
fun InitButton(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(top = 100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MediaScreen()
        }
        Row(
            modifier = Modifier.padding(top = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButtons(
                text = "Picture saved!",
                imageVector = Icons.Default.CameraAlt,
                "Save picture"
            )
            ElevatedButtons(
                text = "Voice activated!",
                imageVector = Icons.Default.KeyboardVoice,
                "Activate voice"
            )
        }
    }
}