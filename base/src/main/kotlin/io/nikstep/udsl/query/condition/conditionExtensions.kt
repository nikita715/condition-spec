package io.nikstep.udsl.query.condition

infix fun <T : Comparable<T>> Condition<T>.and(condition: Condition<T>) =
    And(this, condition)

infix fun <T : Comparable<T>> Condition<T>.or(condition: Condition<T>) =
    Or(this, condition)