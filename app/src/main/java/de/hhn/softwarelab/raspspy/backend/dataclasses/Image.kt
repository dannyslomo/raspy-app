package de.hhn.softwarelab.raspspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty

data class Image(
    @JsonProperty("id") val id: Long,
)
