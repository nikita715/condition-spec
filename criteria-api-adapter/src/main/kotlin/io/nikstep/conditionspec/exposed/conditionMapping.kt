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
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate

/**
 * Apply the corresponding Criteria API where clause for a column
 */
fun <T : Comparable<T>> CriteriaBuilder.matches(
    path: Path<T>, condition: Condition<T>?,
): Predicate? =
    when (condition) {
        is InclusiveCondition -> this.matches(path, condition)
        is ExclusiveCondition -> this.matches(path, condition)
        is NullabilityCondition -> this.matches(path, condition)
        is RangeCondition -> this.matches(path, condition)
        is LikeCondition -> this.matches(path as Path<String>, condition)
        is CompositeCondition -> this.matches(path, condition)
        null -> null
    }

private fun <T : Comparable<T>> CriteriaBuilder.matches(
    path: Path<T>, condition: InclusiveCondition<T>?,
): Predicate? =
    when (condition) {
        is Eq -> this.equal(path, condition.value)
        is In -> path.`in`(condition.values)
        is Between -> this.between(path, condition.value1, condition.value2)
        null -> null
    }

private fun <T : Comparable<T>> CriteriaBuilder.matches(
    path: Path<T>, condition: ExclusiveCondition<T>,
): Predicate? =
    when (condition) {
        is NotEq -> this.notEqual(path, condition.value)
        is NotIn -> path.`in`(condition.values).not()
    }

private fun <T : Comparable<T>> CriteriaBuilder.matches(
    path: Path<T>, condition: NullabilityCondition<T>,
): Predicate? =
    when (condition) {
        is Null -> this.isNull(path)
        is NotNull -> this.isNotNull(path)
    }

private fun <T : Comparable<T>> CriteriaBuilder.matches(
    path: Path<T>, condition: RangeCondition<T>,
): Predicate? =
    when (condition) {
        is GreaterEq -> this.greaterThanOrEqualTo(path, condition.value)
        is Greater -> this.greaterThan(path, condition.value)
        is LessEq -> this.lessThanOrEqualTo(path, condition.value)
        is Less -> this.lessThan(path, condition.value)
    }

private fun CriteriaBuilder.matches(
    path: Path<String>, condition: LikeCondition,
): Predicate? =
    when (condition) {
        is Like -> this.like(path, "%${condition.value}%")
        is NotLike -> this.notLike(path, "%${condition.value}%")
    }

private fun <T : Comparable<T>> CriteriaBuilder.matches(
    path: Path<T>, compositeCondition: CompositeCondition<T>,
): Predicate? =
    when (compositeCondition) {
        is And -> this.and(*compositeCondition.conditions.mapNotNull { condition ->
            this.matches(path, condition)
        }.toTypedArray())

        is Or -> this.or(*compositeCondition.conditions.mapNotNull { condition ->
            this.matches(path, condition)
        }.toTypedArray())
    }
