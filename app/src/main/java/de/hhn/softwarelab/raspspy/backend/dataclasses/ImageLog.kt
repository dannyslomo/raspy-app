package de.hhn.softwarelab.raspspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date


data class ImageLog(
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonProperty("timeStamp") val timeStamp: LocalDateTime,
    @JsonProperty("triggerType") val triggerType: Int,
)
