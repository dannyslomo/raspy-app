package de.hhn.softwarelab.raspspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date


data class Log(
    @JsonProperty("id") val id: Long,
    @JsonProperty("timeStamp") val timeStamp: Date,
    @JsonProperty("triggerType") val triggerType: Boolean,
)
