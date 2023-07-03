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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import de.hhn.softwarelab.raspy.R
import de.hhn.softwarelab.raspy.backend.Services.ImageLogService
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import de.hhn.softwarelab.raspy.ui.livestreamUI.LivestreamActivity

class ImageComposables {
    @Composable
    fun ScrollableLogs(logs: List<ImageLog>) {
        val scrollState = rememberScrollState()
        val context = LocalContext.current
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
                    id = log.id!!,
                    imageUrl = globalValues.serverUrl + "media/" + log.image!!,
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
        id: Int,
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
                    imageUrl = imageUrl, text = text, triggerType = triggerType, id = id
                ) { showDialog = false }
            }
        }
    }

    private @Composable
    fun FullScreenDialog(
        imageUrl: String,
        text: String,
        triggerType: Int,
        id: Int,
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
                        val showDeleteDialog = remember { mutableStateOf(false) }
                        IconButton(
                            onClick = {
                                showDeleteDialog.value = true
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
                        ConfirmDeleteDialog(
                            showDialog = showDeleteDialog.value,
                            onConfirm = {
                                val service = ImageLogService()
                                service.deleteImage(id)
                                showDeleteDialog.value = false
                            },
                            onCancel = {
                                showDeleteDialog.value = false
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ConfirmDeleteDialog(
        showDialog: Boolean,
        onConfirm: () -> Unit,
        onCancel: () -> Unit
    ) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = onCancel,
                title = {
                    Text(text = stringResource(R.string.confirm_delete))
                },
                text = {
                    Text(text = stringResource(R.string.delete_text))
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onConfirm()
                        }
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            onCancel()
                        }
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            )
        }
    }
}