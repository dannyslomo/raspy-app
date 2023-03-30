@file:Suppress("DEPRECATION")

package de.hhn.softwarelab.raspspy.livestreamUI

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.offline.DownloadHelper.createMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun MediaScreen() {
    //val sampleVideo = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val rtspUri = "rtsp://192.168.5.63:8554/video_stream"
    val mediaSource: MediaSource =
        RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(rtspUri))

    val context = LocalContext.current
    val player = ExoPlayer.Builder(context).build()
    player.setMediaSource(mediaSource)
    player.prepare()

    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(rtspUri)
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