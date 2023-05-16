package de.hhn.softwarelab.raspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty

data class Settings(
    /* Lui
    @JsonProperty("username") var deleteInterval: String?
    @JsonProperty("email") var deleteInterval: String?
     */
    @JsonProperty("deleteInterval") var deleteInterval: Int?,
    @JsonProperty("systemActive") var systemActive: Boolean?,
    @JsonProperty("cameraActive") var cameraActive: Boolean?
)
