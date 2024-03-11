package io.nikstep.conditionspec.exposed

import io.nikstep.conditionspec.condition.And
import io.nikstep.conditionspec.condition.Between
import io.nikstep.conditionspec.condition.CompositeCondition
import io.nikstep.conditionspec.condition.Condition
import io.nikstep.conditionspec.condition.Eq
import io.nikstep.conditionspec.condition.ExclusiveCondition
import io.nikstep.conditionspec.condition.Greater
import io.nikstep.conditionspec.condition.GreaterEq
import io.nikstep.conditionspec.condition.In
import io.nikstep.conditionspec.condition.InclusiveCondition
import io.nikstep.conditionspec.condition.Less
import io.nikstep.conditionspec.condition.LessEq
import io.nikstep.conditionspec.condition.Like
import io.nikstep.conditionspec.condition.LikeCondition
import io.nikstep.conditionspec.condition.NotEq
import io.nikstep.conditionspec.condition.NotIn
import io.nikstep.conditionspec.condition.NotLike
import io.nikstep.conditionspec.condition.NotNull
import io.nikstep.conditionspec.condition.Null
import io.nikstep.conditionspec.condition.NullabilityCondition
import io.nikstep.conditionspec.condition.Or
import io.nikstep.conditionspec.condition.RangeCondition
import org.jooq.Field

/**
 * Apply the corresponding jOOQ where clause for a column
 */
infix fun <T : Comparable<T>> Field<T>.matches(
    condition: Condition<T>?,
): org.jooq.Condition? =
    when (condition) {
        is InclusiveCondition -> this matches condition
        is ExclusiveCondition -> this matches condition
        is NullabilityCondition -> this matches condition
        is RangeCondition -> this matches condition
        is LikeCondition -> this matches condition
        is CompositeCondition -> this matches condition
        null -> null
    }

infix fun <T : Comparable<T>> Field<T>.matches(
    condition: InclusiveCondition<T>,
): org.jooq.Condition =
    when (condition) {
        is Eq -> this.equal(condition.value)
        is In -> this.`in`(condition.values)
        is Between -> this.between(condition.value1, condition.value2)
    }

infix fun <T : Comparable<T>> Field<T>.matches(
    condition: ExclusiveCondition<T>,
): org.jooq.Condition =
    when (condition) {
        is NotEq -> this.notEqual(condition.value)
        is NotIn -> this.notIn(condition.values)
    }

infix fun <T : Comparable<T>> Field<T>.matches(
    condition: NullabilityCondition<T>,
): org.jooq.Condition =
    when (condition) {
        is Null -> this.isNull()
        is NotNull -> this.isNotNull()
    }

infix fun <T : Comparable<T>> Field<T>.matches(
    condition: RangeCondition<T>,
): org.jooq.Condition =
    when (condition) {
        is GreaterEq -> this.greaterOrEqual(condition.value)
        is Greater -> this.greaterThan(condition.value)
        is LessEq -> this.lessOrEqual(condition.value)
        is Less -> this.lessThan(condition.value)
    }

infix fun <T : Comparable<T>> Field<T>.matches(
    condition: LikeCondition,
): org.jooq.Condition =
    when (condition) {
        is Like -> this.like("%${condition.value}%")
        is NotLike -> this.notLike("%${condition.value}%")
    }

infix fun <T : Comparable<T>> Field<T>.matches(
    compositeCondition: CompositeCondition<T>,
): org.jooq.Condition =
    when (compositeCondition) {
        is And -> compositeCondition.conditions.mapNotNull { condition ->
            this matches condition
        }.reduce { acc, condition -> acc.and(condition) }

        is Or -> compositeCondition.conditions.mapNotNull { condition ->
            this matches condition
        }.reduce { acc, condition -> acc.or(condition) }
    }
