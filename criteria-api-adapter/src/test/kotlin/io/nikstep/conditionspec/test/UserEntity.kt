package io.nikstep.conditionspec.test

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
open class UserEntity(
    @Id
    val id: Long? = null,
    val name: String?,
) {
    constructor() : this(null, null)
}