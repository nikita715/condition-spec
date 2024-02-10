package io.nikstep.udsl.query.condition

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.between
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notLike

infix fun <T : Comparable<T>> Column<T?>.matches(
    condition: Condition<T>?,
): Op<Boolean>? =
    when (condition) {
        is InclusiveCondition -> (this as Column<T>) matches condition
        is ExclusiveCondition -> (this as Column<T>) matches condition
        is NullabilityCondition -> (this as Column<T>) matches condition
        is RangeCondition -> (this as Column<T>) matches condition
        is LikeCondition -> (this as Column<String>) matches condition
        null -> null
    }

infix fun <T : Comparable<T>> Column<T>.matches(
    condition: Condition<T>?,
): Op<Boolean>? =
    when (condition) {
        is InclusiveCondition -> this matches condition
        is ExclusiveCondition -> this matches condition
        is NullabilityCondition -> this matches condition
        is RangeCondition -> this matches condition
        is LikeCondition -> (this as Column<String>) matches condition
        null -> null
    }

private infix fun <T : Comparable<T>> Column<T>.matches(
    condition: InclusiveCondition<T>?,
): Op<Boolean>? =
    when (condition) {
        is Eq -> this eq condition.value
        is In -> this inList condition.values
        is Between -> this.between(condition.value1, condition.value2)
        null -> null
    }

private infix fun <T : Comparable<T>> Column<T>.matches(
    condition: ExclusiveCondition<T>?,
): Op<Boolean>? =
    when (condition) {
        is NotEq -> this neq condition.value
        is NotIn -> this notInList condition.values
        null -> null
    }

private infix fun <T : Comparable<T>> Column<T>.matches(
    condition: NullabilityCondition<T>?,
): Op<Boolean>? =
    when (condition) {
        is Null -> this.isNull()
        is NotNull -> this.isNotNull()
        null -> null
    }

private infix fun <T : Comparable<T>> Column<T>.matches(
    condition: RangeCondition<T>?,
): Op<Boolean>? =
    when (condition) {
        is GreaterEq -> this greaterEq condition.value
        is Greater -> this greater condition.value
        is LessEq -> this lessEq condition.value
        is Less -> this less condition.value
        null -> null
    }

private infix fun Column<String>.matches(
    condition: LikeCondition?,
): Op<Boolean>? =
    when (condition) {
        is Like -> this like "%${condition.value}%"
        is NotLike -> this notLike "%${condition.value}%"
        null -> null
    }
