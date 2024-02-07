package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.BaseCondition
import io.nikstep.udsl.query.condition.SingleCondition
import java.time.LocalDateTime

interface UserService {

    fun findBy(
        id: SingleCondition<Long>,
        createdAt: BaseCondition<LocalDateTime>? = null,
        firstName: BaseCondition<String>? = null,
        lastName: BaseCondition<String>? = null,
    )

}