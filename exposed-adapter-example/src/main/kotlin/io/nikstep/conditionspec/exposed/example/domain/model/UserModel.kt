package io.nikstep.conditionspec.exposed.example.domain.model

import java.time.LocalDateTime

data class UserModel(
    val id: Long,
    val firstName: String?,
    val lastName: String,
    val birthDate: LocalDateTime?,
    val createdAt: LocalDateTime,
)