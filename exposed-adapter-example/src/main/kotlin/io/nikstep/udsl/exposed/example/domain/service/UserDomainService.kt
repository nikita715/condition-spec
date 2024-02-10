package io.nikstep.udsl.exposed.example.domain.service

import io.nikstep.udsl.exposed.example.domain.model.UserModel
import io.nikstep.udsl.query.condition.Condition
import io.nikstep.udsl.query.condition.RangeCondition
import io.nikstep.udsl.query.condition.SingleCondition
import java.time.LocalDateTime

interface UserDomainService {

    fun findOneBy(
        id: SingleCondition<Long>? = null,
        firstName: Condition<String>? = null,
        lastName: Condition<String>? = null,
        birthDate: RangeCondition<LocalDateTime>? = null,
    ): UserModel?

}