package de.hhn.softwarelab.raspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty

data class Settings(
    @JsonProperty("deleteInterval") var deleteInterval: Int?,
    @JsonProperty("systemActive") var systemActive: Boolean?,
    @JsonProperty("cameraActive") var cameraActive: Boolean?,
    @JsonProperty("darkMode") var darkMode: Boolean?,
    @JsonProperty("language") var language: String?,
    @JsonProperty("policy") var policy: Boolean?
)
