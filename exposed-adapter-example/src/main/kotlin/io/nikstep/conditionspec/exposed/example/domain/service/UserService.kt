package io.nikstep.conditionspec.exposed.example.domain.service

import io.nikstep.conditionspec.exposed.example.domain.model.UserModel
import io.nikstep.conditionspec.condition.Condition
import io.nikstep.conditionspec.condition.RangeCondition
import io.nikstep.conditionspec.condition.SingleCondition
import java.time.LocalDateTime

interface UserService {

    fun findOneBy(
        id: SingleCondition<Long>? = null,
        firstName: Condition<String>? = null,
        lastName: Condition<String>? = null,
        birthDate: RangeCondition<LocalDateTime>? = null,
    ): UserModel?

}