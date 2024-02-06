package io.nikstep.udsl.model

import java.time.LocalDateTime

data class User(
    val id: Long,
    val firstName: String?,
    val lastName: String,
    val birthDate: LocalDateTime?,
    val createdAt: LocalDateTime,
)