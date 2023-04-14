package de.hhn.softwarelab.raspspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.Date


data class ImageLog(
    @JsonProperty("id") val id: Long,
    @JsonProperty("timeStamp") val timeStamp: LocalDate,
    @JsonProperty("triggerType") val triggerType: Int,
)
