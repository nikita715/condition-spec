package io.nikstep.conditionspec.exposed.example.api.model

import java.time.LocalDateTime

data class UserApiModel(
    val id: Long,
    val firstName: String?,
    val lastName: String,
    val birthDate: LocalDateTime?,
)