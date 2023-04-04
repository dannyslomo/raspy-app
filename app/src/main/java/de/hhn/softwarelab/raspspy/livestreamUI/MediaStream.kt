@file:Suppress("DEPRECATION")

package de.hhn.softwarelab.raspspy.livestreamUI

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.offline.DownloadHelper.createMediaSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy

@Composable
fun MediaScreen() {

    //Video src
    //val sampleVideo = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    //val rtspUri = "rtsp://192.168.73.63:5000/video_stream"
    val sampleVideo by remember { mutableStateOf("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }

    val mediaSource: MediaSource = RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(sampleVideo))
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
    val mediaItem = MediaItem.fromUri(sampleVideo)
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
    }
    )
}


@Composable
fun MediaScreen1() {

    // Video src
    var videoUrl by remember { mutableStateOf("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }
    val rtspUri by remember { mutableStateOf("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }

    val mediaSource: MediaSource = remember { createMediaSource(rtspUri) }
    //val mediaSource: MediaSource = RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(rtspUri))
    val mediaItem = MediaItem.fromUri(rtspUri)

    val context = LocalContext.current

    // Error handling
    val loadErrorHandlingPolicy: LoadErrorHandlingPolicy =
        object : DefaultLoadErrorHandlingPolicy() {
            override fun getRetryDelayMsFor(loadErrorInfo: LoadErrorHandlingPolicy.LoadErrorInfo): Long {
                // Implement custom back-off logic here.
                return 0
            }
        }

    // Player initialization
    val player = remember { createPlayer(context, loadErrorHandlingPolicy) }
    player.setMediaSource(mediaSource)
    player.setMediaItem(mediaItem)



    val playerView = remember { createPlayerView(context, player) }

    // Player state
    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady
    }

    AndroidView(
        factory = {
            playerView
        }
    )
}

fun createMediaSource(rtspUri: String): MediaSource {
    val mediaItem = when {
        rtspUri.isNotBlank() -> MediaItem.fromUri(rtspUri)
        else -> throw IllegalArgumentException("RTSP URI must be provided")
    }
    return RtspMediaSource.Factory().createMediaSource(mediaItem)
}

fun createPlayer(context: Context, loadErrorHandlingPolicy: LoadErrorHandlingPolicy): ExoPlayer {
    val renderersFactory = DefaultRenderersFactory(context).forceEnableMediaCodecAsynchronousQueueing()
    val mediaSourceFactory = DefaultMediaSourceFactory(context).setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
    return ExoPlayer.Builder(context, renderersFactory).setMediaSourceFactory(mediaSourceFactory).build()
}

fun createPlayerView(context: Context, player: ExoPlayer): PlayerView {
    val playerView = PlayerView(context)
    playerView.player = player
    return playerView
}
