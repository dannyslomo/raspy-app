@file:Suppress("DEPRECATION")

package de.hhn.softwarelab.raspy.ui.livestreamUI.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy
import com.google.android.exoplayer2.upstream.HttpDataSource.HttpDataSourceException
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
import de.hhn.softwarelab.raspy.backend.dataclasses.globalValues
import java.io.IOException
import java.net.UnknownHostException
import com.google.android.exoplayer2.ExoPlaybackException

//TODO
@Composable
fun MediaScreen() {

    //Video src
    val rtspUri by remember { mutableStateOf(globalValues.livestreamUrl) }

    val mediaSource: MediaSource = RtspMediaSource.Factory().setDebugLoggingEnabled(true).setTimeoutMs(10000).createMediaSource(MediaItem.fromUri(rtspUri))
    val context = LocalContext.current

    //Error handling
    val loadErrorHandlingPolicy: LoadErrorHandlingPolicy =
        object : DefaultLoadErrorHandlingPolicy() {
            fun getRetryDelayMsFor(
                dataType: Int,
                loadDurationMs: Long,
                exception: IOException?,
                errorCount: Int
            ): Long {
                Log.i("MediaStream", "getRetryDelayMsFor: Test1")
                // checking if it is a connectivity issue
                return if (exception is UnknownHostException) {
                    Log.i("MediaStream", "getRetryDelayMsFor: Failed to connect to server retrying...")
                    2000 // Retry every 5 seconds.
                } else {
                    Log.i("MediaStream", "getRetryDelayMsFor: Test2")
                    C.TIME_UNSET // Anything else is surfaced.
                }
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
