package de.hhn.softwarelab.raspy.ImageLogsUI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
//import coil.compose.rememberAsyncImagePainter
import de.hhn.softwarelab.raspy.ui.theme.RaspSPYTheme

class ImageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspSPYTheme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    RaspSPYTheme {
        Image(
            painter = rememberAsyncImagePainter("https://picsum.photos/id/237/200/300"),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
                .fillMaxWidth()
        )
    }
}
