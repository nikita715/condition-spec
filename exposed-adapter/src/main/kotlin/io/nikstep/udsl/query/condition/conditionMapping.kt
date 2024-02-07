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
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.compoundOr

infix fun <T : Comparable<T>> Column<T?>.eq(
    condition: BaseCondition<T>?,
): Op<Boolean>? =
    when (condition) {
        is InclusiveCondition -> (this as Column<T>) eq condition
        is ExclusiveCondition -> (this as Column<T>) eq condition
        is NullabilityCondition -> (this as Column<T>) eq condition
        is RangeCondition -> (this as Column<T>) eq condition
        is LikeCondition -> (this as Column<String>) eq condition
        is CompositeCondition -> (this as Column<T>) eq condition
        null -> null
    }

infix fun <T : Comparable<T>> Column<T>.eq(
    condition: BaseCondition<T>?,
): Op<Boolean>? =
    when (condition) {
        is InclusiveCondition -> this eq condition
        is ExclusiveCondition -> this eq condition
        is NullabilityCondition -> this eq condition
        is RangeCondition -> this eq condition
        is LikeCondition -> (this as Column<String>) eq condition
        is CompositeCondition -> this eq condition
        null -> null
    }

private infix fun <T : Comparable<T>> Column<T>.eq(
    condition: InclusiveCondition<T>,
): Op<Boolean> =
    when (condition) {
        is Eq -> this eq condition.value
        is In -> this inList condition.values
        is Between -> this.between(condition.value1, condition.value2)
    }

private infix fun <T : Comparable<T>> Column<T>.eq(
    condition: ExclusiveCondition<T>,
): Op<Boolean> =
    when (condition) {
        is NotEq -> this neq condition.value
        is NotIn -> this notInList condition.values
    }

private infix fun <T : Comparable<T>> Column<T>.eq(
    condition: NullabilityCondition<T>,
): Op<Boolean> =
    when (condition) {
        is Null -> this.isNull()
        is NotNull -> this.isNotNull()
    }

private infix fun <T : Comparable<T>> Column<T>.eq(
    condition: RangeCondition<T>,
): Op<Boolean> =
    when (condition) {
        is GreaterEq -> this greaterEq condition.value
        is Greater -> this greater condition.value
        is LessEq -> this lessEq condition.value
        is Less -> this less condition.value
    }

private infix fun Column<String>.eq(
    condition: LikeCondition,
): Op<Boolean> =
    when (condition) {
        is Like -> this like condition.value
        is NotLike -> this notLike condition.value
    }

private infix fun <T : Comparable<T>, C : BaseCondition<T>> Column<T>.eq(
    compositeCondition: CompositeCondition<T>,
): Op<Boolean> =
    when (compositeCondition) {
        is And -> compositeCondition.conditions.mapNotNull { condition -> this eq condition }.compoundAnd()
        is Or -> compositeCondition.conditions.mapNotNull { condition -> this eq condition }.compoundOr()
    }
