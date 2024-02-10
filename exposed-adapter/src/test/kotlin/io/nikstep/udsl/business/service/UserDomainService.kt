package io.nikstep.udsl.business.service

import io.nikstep.udsl.business.dto.UserSearchRequest
import io.nikstep.udsl.business.model.UserBusinessModel
import io.nikstep.udsl.domain.model.UserModel
import io.nikstep.udsl.domain.service.UserService
import io.nikstep.udsl.query.condition.LessEq
import io.nikstep.udsl.query.condition.Like
import io.nikstep.udsl.query.condition.NotIn
import io.nikstep.udsl.query.condition.and
import java.time.LocalDateTime

class UserDomainService(
    private val userService: UserService,
) {

    fun findFirstUserByParameters(
        request: UserSearchRequest,
    ): UserBusinessModel {
        val userModel = userService.findOneBy(
            firstName = NotIn(request.firstNameNot1, request.firstNameNot2) and Like(request.firstNameLike),
            lastName = Like(request.lastNameLike),
            birthDate = LessEq(LocalDateTime.now().minusYears(request.minimumAge)),
        ) ?: throw RuntimeException("User is not found")

        return userModel.toBusinessModel()
    }

    private fun UserModel.toBusinessModel() =
        UserBusinessModel(
            id = id,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )

}