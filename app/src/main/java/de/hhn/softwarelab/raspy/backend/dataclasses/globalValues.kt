package de.hhn.softwarelab.raspy.backend.dataclasses

class globalValues {
    companion object{
        //val livestreamUrl = "rtsp://192.168.109.88:8554/video_stream"
        const val  livestreamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        const val serverUrl = "http://192.168.178.35:8000//"
        const val login_failed = 600
        var login_successful = 0
        var settingsId = 0
    }
}