package de.hhn.softwarelab.raspy.backend.dataclasses

class globalValues {
    companion object{
        const val  livestreamUrl = "rtsp://193.196.55.18:8554/raspy"
        //const val  livestreamUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        const val serverUrl = "http://193.196.55.18:8888/"
        const val login_failed = 600
        var login_successful = 0
        var settingsId = 0
    }
}