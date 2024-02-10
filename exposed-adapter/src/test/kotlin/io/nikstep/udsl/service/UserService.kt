package io.nikstep.udsl.service

import io.nikstep.udsl.model.UserModel
import io.nikstep.udsl.query.condition.Condition
import io.nikstep.udsl.query.condition.RangeCondition
import io.nikstep.udsl.query.condition.SingleCondition
import java.time.LocalDateTime

interface UserService {

    fun findOneBy(
        id: SingleCondition<Long>? = null,
        firstName: Condition<String>? = null,
        lastName: Condition<String>? = null,
        birthDate: RangeCondition<LocalDateTime>? = null,
    ): UserModel?

}