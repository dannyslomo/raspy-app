package de.hhn.softwarelab.raspspy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import de.hhn.softwarelab.raspspy.ui.theme.RaspSPYTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspSPYTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(modifier = Modifier.padding(top = 150.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            MediaScreen()
                        }
                        Row(modifier = Modifier.padding(top = 100.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ElevatedButtons(text = "Picture saved!", imageVector = Icons.Default.CameraAlt)
                            ElevatedButtons(text = "Voice activated!", imageVector = Icons.Default.KeyboardVoice)
                            ElevatedButtons(text = "Picture saved!", imageVector = Icons.Default.PictureInPicture)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MediaScreen() {
    val sampleVideo =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val context = LocalContext.current
    val player = ExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(sampleVideo)
    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    player.setMediaItem(mediaItem)
    playerView.player = player
    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady

    }
    AndroidView(factory = {
        playerView
    })

}

@Composable
fun ElevatedButtons(text: String, imageVector: ImageVector){
    val contextForToast = LocalContext.current.applicationContext

    ElevatedButton(
        modifier = Modifier
            .padding(15.dp)
            .size(size = 100.dp),
        onClick = {
            Toast.makeText(contextForToast, text, Toast.LENGTH_SHORT).show()
        }
    ) {
        Icon(
            modifier = Modifier.size(size = 100.dp),
            imageVector = imageVector,
            contentDescription = ""
        )
    }
}