package io.nikstep.conditionspec.exposed.example.infrastructure.service

import io.nikstep.conditionspec.exposed.example.domain.model.UserModel
import io.nikstep.conditionspec.exposed.example.domain.service.UserService
import io.nikstep.conditionspec.exposed.example.infrastructure.table.UserTable
import io.nikstep.conditionspec.exposed.matches
import io.nikstep.conditionspec.condition.Condition
import io.nikstep.conditionspec.condition.RangeCondition
import io.nikstep.conditionspec.condition.SingleCondition
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class UserServiceImpl : UserService {

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