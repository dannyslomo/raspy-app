package de.hhn.softwarelab.raspy.ui.ImageLogsUI


import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.ui.livestreamUI.LivestreamActivity

class ImageComposables {
    val BASE_URL = "http://"

    @Composable
    fun ScrollableLogs(logs: List<ImageLog>) {
        val scrollState = rememberScrollState()
        val context = LocalContext.current
        androidx.compose.material.TopAppBar(
            title = { androidx.compose.material.Text(text = "Gallery") },
            navigationIcon = {
                androidx.compose.material.IconButton(onClick = {
                    val intent = Intent(context, LivestreamActivity::class.java)
                    context.startActivity(intent)
                }) {
                    androidx.compose.material.Icon(
                        Icons.Filled.ArrowBack, contentDescription = "Back"
                    )
                }
            },
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (log in logs) {
                ImageCard(
                    text = log.timeStamp!!.toString(),
                    triggerType = log.triggerType!!,
                    imageUrl = globalValues.serverUrl + "media/" + log.image!!
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }


    @Composable
    //triggerType: 0 = Person, 1 = Camera, 2 = Ring
    fun ImageCard(
        imageUrl: String,
        text: String,
        triggerType: Int,
        modifier: Modifier = Modifier,
    ) {
        var showDialog by remember { mutableStateOf(false) }
        Card(shape = MaterialTheme.shapes.medium,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 8.dp)
                .clickable { showDialog = true }) {
            Column(
            ) {
                Row() {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "CCTV Image from $text",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row() {
                    when (triggerType) {
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
                        text = text, modifier = Modifier.padding(16.dp)
                    )
                }
            }
            if (showDialog) {
                FullScreenDialog(
                    imageUrl = imageUrl, text = text, triggerType = triggerType
                ) { showDialog = false }
            }
        }
    }

    private @Composable
    fun FullScreenDialog(
        imageUrl: String,
        text: String,
        triggerType: Int,
        modifier: Modifier = Modifier,
        onClose: () -> Unit
    ) {
        Dialog(
            onDismissRequest = onClose
        ) {
            Box(
                modifier.fillMaxWidth()
            ) {
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "CCTV Image from $text",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .padding(top = if (isLandscape) 0.dp else 216.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(bottom = if (isLandscape) 0.dp else 216.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        when (triggerType) {
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
                            modifier = Modifier.padding(start = 16.dp),
                            color = Color.White
                        )
                        IconButton(
                            onClick = {

                            },
                            modifier = Modifier
                                .padding(start = 50.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }


}