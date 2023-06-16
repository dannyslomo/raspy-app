package de.hhn.softwarelab.raspy.backend.dataclasses

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    @JsonProperty("firstName") var firstName: String?,
    @JsonProperty("lastName") var lastName: String?,
    @JsonProperty("username") var username: String?,
    @JsonProperty("password") var password: String?,
    @JsonProperty("email") var email: String?,
    @JsonProperty("settingsId") var settingsId: String?,
)
