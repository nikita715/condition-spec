package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.BaseCondition
import io.nikstep.udsl.query.condition.RangeCondition
import io.nikstep.udsl.query.condition.SingleCondition
import java.time.LocalDateTime

interface UserService {

    fun findBy(
        id: SingleCondition<Long>,
        createdAt: RangeCondition<LocalDateTime>? = null,
        firstName: BaseCondition<String>? = null,
    )

}