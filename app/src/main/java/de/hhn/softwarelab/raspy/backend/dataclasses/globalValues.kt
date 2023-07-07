package de.hhn.softwarelab.raspy.backend.dataclasses

class globalValues {
    companion object{
        val livestreamUrl = "rtsp://192.168.109.88:8554/video_stream"
        const val serverUrl = "http://193.196.55.18:8888/"
        const val login_failed = 600
        var login_successful = 0
        var settingsId = 0
    }
}