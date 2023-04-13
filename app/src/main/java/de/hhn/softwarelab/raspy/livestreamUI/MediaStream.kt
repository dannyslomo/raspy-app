@file:Suppress("DEPRECATION")

package de.hhn.softwarelab.raspy.livestreamUI

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy

@Composable
fun MediaScreen() {

    //Video src
    //val rtspUri = "rtsp://192.168.73.63:5000/video_stream"
    val rtspUri by remember { mutableStateOf("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }

    val mediaSource: MediaSource = RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(rtspUri))
    val context = LocalContext.current

    //Error handling
    val loadErrorHandlingPolicy: LoadErrorHandlingPolicy =
        object : DefaultLoadErrorHandlingPolicy() {
            override fun getRetryDelayMsFor(loadErrorInfo: LoadErrorHandlingPolicy.LoadErrorInfo): Long {
                // Implement custom back-off logic here.
                return 0
            }
        }

    //Enabling asynchronous buffer queueing
    val renderersFactory =
        DefaultRenderersFactory(context).forceEnableMediaCodecAsynchronousQueueing()


    val player = ExoPlayer.Builder(context, renderersFactory)
        .setMediaSourceFactory(DefaultMediaSourceFactory(context).setLoadErrorHandlingPolicy(loadErrorHandlingPolicy))
        .build()
    player.setMediaSource(mediaSource)

    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(rtspUri)
    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    player.setMediaItem(mediaItem)
    playerView.player = player
    playerView.useController = false
    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady
    }

    AndroidView(
        factory = {
        playerView
    },
        modifier = Modifier.fillMaxWidth(1f)

    )
}
