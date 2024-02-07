package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.BaseCondition
import io.nikstep.udsl.query.condition.SingleCondition
import io.nikstep.udsl.query.condition.eq
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class UserServiceImpl : UserService {

    override fun findBy(
        id: SingleCondition<Long>,
        createdAt: BaseCondition<LocalDateTime>?,
        firstName: BaseCondition<String>?,
        lastName: BaseCondition<String>?
    ) {
        UserTable.select {
            listOfNotNull(
                UserTable.id eq id,
                UserTable.createdAt eq createdAt,
                UserTable.firstName eq firstName,
                UserTable.lastName eq lastName,
            ).compoundAnd()
        }
    }

}