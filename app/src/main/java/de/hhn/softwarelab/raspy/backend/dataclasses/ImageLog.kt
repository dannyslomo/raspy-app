package de.hhn.softwarelab.raspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDateTime


data class ImageLog(
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonProperty("timeStamp") var timeStamp: LocalDateTime?,
    @JsonProperty("triggerType") var triggerType: Int?,
    @JsonProperty("image") var image: String?,
    @JsonProperty("id") var id: Int?
)
