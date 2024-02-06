package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.BaseCondition
import io.nikstep.udsl.query.condition.RangeCondition
import io.nikstep.udsl.query.condition.SingleCondition
import io.nikstep.udsl.query.condition.eq
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class UserServiceImpl : UserService {

    override fun findBy(
        id: SingleCondition<Long>,
        createdAt: RangeCondition<LocalDateTime>,
        firstName: BaseCondition<String>,
    ) {
        UserTable.select {
            listOfNotNull(
                UserTable.id eq id,
                UserTable.firstName eq firstName,
                UserTable.createdAt eq createdAt,
            ).compoundAnd()
        }
    }

}