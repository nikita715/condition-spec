package io.nikstep.udsl.query.condition

/*
    Base condition interface
 */
sealed interface Condition<T : Comparable<T>>

/*
    Condition that includes specific instances
 */
sealed interface InclusiveCondition<T : Comparable<T>> : Condition<T>

/*
    Condition that uses like statement
 */
sealed interface LikeCondition : Condition<String>

/*
    Condition that includes single instance
 */
sealed interface SingleCondition<T : Comparable<T>> : InclusiveCondition<T>

/*
    Condition that includes multiple instances
 */
sealed interface MultiCondition<T : Comparable<T>> : InclusiveCondition<T>

/*
    Condition that excludes specific instances
 */
sealed interface ExclusiveCondition<T : Comparable<T>> : Condition<T>

/*
    Condition that includes a range of instances
 */
sealed interface RangeCondition<T : Comparable<T>> : Condition<T> {
    val value: T
}

/*
    Condition that includes instances based on their nullability
 */
sealed interface NullabilityCondition<T : Comparable<T>> : Condition<T>

data class Eq<T : Comparable<T>>(
    val value: T,
) : SingleCondition<T>

data class NotEq<T : Comparable<T>>(
    val value: T,
) : ExclusiveCondition<T>

data class In<T : Comparable<T>>(
    val values: List<T>,
) : MultiCondition<T> {
    constructor(vararg values: T) : this(values = values.toList())
}

data class NotIn<T : Comparable<T>>(
    val values: List<T>,
) : ExclusiveCondition<T> {
    constructor(vararg values: T) : this(values = values.toList())
}

data class GreaterEq<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

data class Greater<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

data class LessEq<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

data class Less<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

data class Between<T : Comparable<T>>(
    val value1: T,
    val value2: T,
) : MultiCondition<T> {
    constructor(vararg values: T) : this(values[0], values[1])
}

data class Like(
    val value: String,
) : LikeCondition

data class NotLike(
    val value: String,
) : LikeCondition

class Null<T : Comparable<T>> : NullabilityCondition<T>

class NotNull<T : Comparable<T>> : NullabilityCondition<T>
