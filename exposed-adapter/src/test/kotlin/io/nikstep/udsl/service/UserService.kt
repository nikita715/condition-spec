package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.Condition
import io.nikstep.udsl.query.condition.SingleCondition
import java.time.LocalDateTime

interface UserService {

    fun findBy(
        id: SingleCondition<Long>,
        createdAt: Condition<LocalDateTime>? = null,
        firstName: Condition<String>? = null,
        lastName: Condition<String>? = null,
    )

}