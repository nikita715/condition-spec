package io.nikstep.udsl.business.dto

data class UserSearchRequest(
    val firstNameNot1: String,
    val firstNameNot2: String,
    val lastNameLike: String,
    val minimumAge: Long,
)