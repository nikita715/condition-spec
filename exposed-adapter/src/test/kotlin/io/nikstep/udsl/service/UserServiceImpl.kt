package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.Condition
import io.nikstep.udsl.query.condition.SingleCondition
import io.nikstep.udsl.query.condition.matches
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class UserServiceImpl : UserService {

    override fun findBy(
        id: SingleCondition<Long>,
        createdAt: Condition<LocalDateTime>?,
        firstName: Condition<String>?,
        lastName: Condition<String>?
    ) {
        UserTable.select {
            listOfNotNull(
                UserTable.id matches id,
                UserTable.createdAt matches createdAt,
                UserTable.firstName matches firstName,
                UserTable.lastName matches lastName,
            ).compoundAnd()
        }
    }

}