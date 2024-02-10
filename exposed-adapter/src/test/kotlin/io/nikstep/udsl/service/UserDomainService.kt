package io.nikstep.udsl.service

import io.nikstep.udsl.query.condition.Eq
import io.nikstep.udsl.query.condition.Like
import io.nikstep.udsl.query.condition.NotIn

class UserDomainService(
    private val userService: UserService,
) {

    fun getUser() {
        userService.findBy(
            id = Eq(1L),
            firstName = NotIn("hello", "bye"),
            lastName = Like("qwe"),
        )
    }

}