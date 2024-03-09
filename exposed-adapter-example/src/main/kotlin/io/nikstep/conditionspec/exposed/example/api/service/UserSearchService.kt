package io.nikstep.conditionspec.exposed.example.api.service

import io.nikstep.conditionspec.exposed.example.api.dto.UserSearchRequest
import io.nikstep.conditionspec.exposed.example.api.model.UserApiModel
import io.nikstep.conditionspec.exposed.example.domain.model.UserModel
import io.nikstep.conditionspec.exposed.example.domain.service.UserService
import io.nikstep.conditionspec.condition.LessEq
import io.nikstep.conditionspec.condition.Like
import io.nikstep.conditionspec.condition.NotIn
import io.nikstep.conditionspec.condition.and
import java.time.LocalDateTime

class UserSearchService(
    private val userService: UserService,
) {

    fun findFirstUserByParameters(
        request: UserSearchRequest,
    ): UserApiModel {
        val userModel = userService.findOneBy(
            firstName = NotIn(request.firstNameNot1, request.firstNameNot2) and Like(request.firstNameLike),
            lastName = Like(request.lastNameLike),
            birthDate = LessEq(LocalDateTime.now().minusYears(request.minimumAge)),
        ) ?: throw RuntimeException("User is not found")

        return userModel.toApiModel()
    }

    private fun UserModel.toApiModel() =
        UserApiModel(
            id = id,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )

}