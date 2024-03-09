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
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.compoundOr

infix fun <T : Comparable<T>> Column<T?>.matches(
    condition: Condition<T>?,
): Op<Boolean>? = (this as Column<T>) matches condition

infix fun <T : Comparable<T>> Column<T>.matches(
    condition: Condition<T>?,
): Op<Boolean>? =
    when (condition) {
        is InclusiveCondition -> this matches condition
        is ExclusiveCondition -> this matches condition
        is NullabilityCondition -> this matches condition
        is RangeCondition -> this matches condition
        is LikeCondition -> (this as Column<String>) matches condition
        is CompositeCondition -> this matches condition
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

private infix fun <T : Comparable<T>> Column<T>.matches(
    compositeCondition: CompositeCondition<T>,
): Op<Boolean> =
    when (compositeCondition) {
        is And -> compositeCondition.conditions.mapNotNull { condition -> this matches condition }.compoundAnd()
        is Or -> compositeCondition.conditions.mapNotNull { condition -> this matches condition }.compoundOr()
    }
