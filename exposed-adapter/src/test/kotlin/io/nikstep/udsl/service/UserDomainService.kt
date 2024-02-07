package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.And
import io.nikstep.udsl.query.condition.Eq
import io.nikstep.udsl.query.condition.Like
import io.nikstep.udsl.query.condition.NotEq
import io.nikstep.udsl.query.condition.NotIn
import io.nikstep.udsl.query.condition.NotNull

class UserDomainService(
    private val userService: UserService,
) {

    fun getUser() {
        userService.findBy(
            id = Eq(1L),
            firstName = And(NotIn("hello", "bye"), NotEq("asb"), Like("b"), NotNull()),
            lastName = Like("qwe")
        )
    }

}