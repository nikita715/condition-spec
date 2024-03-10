package io.nikstep.conditionspec.condition

/**
 * Combine the condition with another condition into a single And condition
 */
infix fun <T : Comparable<T>> Condition<T>.and(condition: Condition<T>) =
    And(this, condition)

/**
 * Combine the condition with another condition into a single Or condition
 */
infix fun <T : Comparable<T>> Condition<T>.or(condition: Condition<T>) =
    Or(this, condition)