package io.nikstep.udsl.business.model

import java.time.LocalDateTime

data class UserBusinessModel(
    val id: Long,
    val firstName: String?,
    val lastName: String,
    val birthDate: LocalDateTime?,
)