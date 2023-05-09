package de.hhn.softwarelab.raspy.ImageLogsUI


import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import de.hhn.softwarelab.raspy.backend.dataclasses.ImageLog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import coil.compose.rememberAsyncImagePainter


class ImageComposables {
    val BASE_URL = "http://"

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
                ImageCard(
                    text = log.timeStamp!!.toString(),
                    triggerType = log.triggerType!!,
                    imageUrl = "http://192.168.235.209:8000/media/" + log.image!!
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
        Card(
            modifier = modifier,
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "CCTV Image from $text",
                    modifier = Modifier.size(128.dp)
                )
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
                        text = text,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
