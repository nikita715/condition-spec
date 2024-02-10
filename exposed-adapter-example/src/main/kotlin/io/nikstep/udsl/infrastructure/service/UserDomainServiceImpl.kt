package io.nikstep.udsl.infrastructure.service

import io.nikstep.udsl.domain.model.UserModel
import io.nikstep.udsl.domain.service.UserDomainService
import io.nikstep.udsl.infrastructure.table.UserTable
import io.nikstep.udsl.query.condition.Condition
import io.nikstep.udsl.query.condition.RangeCondition
import io.nikstep.udsl.query.condition.SingleCondition
import io.nikstep.udsl.query.condition.matches
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class UserDomainServiceImpl : UserDomainService {

    override fun findOneBy(
        id: SingleCondition<Long>?,
        firstName: Condition<String>?,
        lastName: Condition<String>?,
        birthDate: RangeCondition<LocalDateTime>?,
    ): UserModel? =
        UserTable.select {
            listOfNotNull(
                UserTable.id matches id,
                UserTable.firstName matches firstName,
                UserTable.lastName matches lastName,
                UserTable.birthDate matches birthDate,
            ).compoundAnd()
        }.limit(1).firstOrNull()?.toUserDomainModel()

    private fun ResultRow.toUserDomainModel() =
        UserModel(
            id = get(UserTable.id),
            firstName = get(UserTable.firstName),
            lastName = get(UserTable.lastName),
            birthDate = get(UserTable.birthDate),
            createdAt = get(UserTable.createdAt),
        )

}