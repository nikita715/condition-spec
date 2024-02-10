package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.LessEq
import io.nikstep.udsl.query.condition.Like
import io.nikstep.udsl.query.condition.NotIn
import java.time.LocalDateTime

class UserDomainService(
    private val userService: UserService,
) {

    fun doSomeOperationWithUser() {
        val user = userService.findOneBy(
            firstName = NotIn("Bob", "Bill"),
            lastName = Like("son"),
            birthDate = LessEq(LocalDateTime.now().minusYears(20)),
        )
    }

}