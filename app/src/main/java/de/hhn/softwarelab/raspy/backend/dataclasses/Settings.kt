package de.hhn.softwarelab.raspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty

data class Settings(
    @JsonProperty("deleteInterval") val deleteInterval: Int?,
    @JsonProperty("systemActive") val systemActive: Boolean?,
    @JsonProperty("cameraActive") val cameraActive: Boolean?
)
