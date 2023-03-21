package de.hhn.softwarelab.raspspy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import de.hhn.softwarelab.raspspy.ui.theme.RaspSPYTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspSPYTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    val painter = painterResource(id = R.drawable.cctv)
                    val title = "Livestream: 15/03/2023 15:12"
                    StreamCard(painter = painter, title = title)
                    //LivePlayer()
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                        ){
                        StandardButton(text = "Bild machen")
                        StandardButton(text = "Logs ansehen")
                    }
                }
                
            }
        }
    }
}

@Composable
fun StreamCard(
    painter: Painter,
    title: String,
    modifier: Modifier = Modifier
){
    Card (
        modifier = modifier
            .fillMaxHeight(0.23f)
            .padding(10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 14.dp
        )
            ) {
        Box(modifier = Modifier
            .height(250.dp)
            .border(3.dp, Color.White)) {
            LivePlayer()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f)
                    .background(color = Color(0, 0, 0, 196)),
            ){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = 4.dp, y = 2.dp),
                    contentAlignment = Alignment.TopStart){
                    Text(text = title, style = androidx.compose.ui.text.TextStyle(Color.White, fontSize = 11.sp))
                }
            }
        }
    }
}

@Composable
fun StandardButton(
    text: String,
){
    Button(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        onClick = { /*TODO*/ }) {
        Text(text = text)
    }
}

@Composable
fun LivePlayer(){
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        val mContext = LocalContext.current

        val hlsUri = "http://192.168.196.209:8000/livestream.m3u8"
        val ipUri = "http:/192.168.196.209:5000/video_stream"
        val dashUri = ""


        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(hlsUri))

        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(ipUri))

        val mediaSourceDash: MediaSource = DashMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(dashUri))

        // Declaring ExoPlayer
        val mExoPlayer = remember(hlsMediaSource) {
            ExoPlayer.Builder(mContext).build().apply {
                val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
            }
        }
        mExoPlayer.setMediaSource(mediaSourceDash)

        // Implementing ExoPlayer
        AndroidView(factory = { context ->
            PlayerView(context).apply {
                player = mExoPlayer
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RaspSPYTheme {

    }
}