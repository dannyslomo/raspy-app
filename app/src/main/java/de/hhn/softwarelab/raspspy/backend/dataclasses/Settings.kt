package de.hhn.softwarelab.raspspy.backend.dataclasses

import android.app.Notification
import com.fasterxml.jackson.annotation.JsonProperty
import org.intellij.lang.annotations.Language

data class Settings(
    @JsonProperty("id") val id: Long,
    @JsonProperty("deleteInterval") val deleteInterval: Int,
    @JsonProperty("systemActive") val systemActive: Boolean,
    @JsonProperty("cameraActive") val cameraActive: Boolean
)
