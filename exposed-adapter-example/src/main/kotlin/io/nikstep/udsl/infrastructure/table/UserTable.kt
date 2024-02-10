package io.nikstep.udsl.infrastructure.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : Table("users") {
    val id = long("id")
        .autoIncrement()

    val firstName = text("first_name")
        .nullable()

    val lastName = text("last_name")

    val birthDate = datetime("created_at")
        .nullable()

    val createdAt = datetime("created_at")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}