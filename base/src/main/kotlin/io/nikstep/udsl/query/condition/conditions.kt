package io.nikstep.udsl.query.condition

/*
    Base condition interface
 */
sealed interface BaseCondition<T>

/*
    Single condition
 */
sealed interface Condition<T> : BaseCondition<T>

/*
    Condition that uses multiple conditions
 */
sealed interface CompositeCondition<T> : BaseCondition<T>

/*
    Condition that includes specific instances
 */
sealed interface InclusiveCondition<T> : Condition<T>

/*
    Condition that uses like statement
 */
sealed interface LikeCondition : Condition<String>

/*
    Condition that includes single instance
 */
sealed interface SingleCondition<T> : InclusiveCondition<T>

/*
    Condition that includes multiple instances
 */
sealed interface MultiCondition<T> : InclusiveCondition<T>

/*
    Condition that excludes specific instances
 */
sealed interface ExclusiveCondition<T> : Condition<T>

/*
    Condition that includes a range of instances
 */
sealed interface RangeCondition<T : Comparable<T>> : Condition<T> {
    val value: T
}

/*
    Condition that includes instances based on their nullability
 */
sealed interface NullabilityCondition<T> : Condition<T>

data class Eq<T>(
    val value: T,
) : SingleCondition<T>

data class NotEq<T>(
    val value: T,
) : ExclusiveCondition<T>

data class In<T>(
    val values: List<T>,
) : MultiCondition<T> {
    constructor(vararg values: T) : this(values = values.toList())
}

data class NotIn<T>(
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

data class Between<T>(
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

class Null<T> : NullabilityCondition<T>

class NotNull<T> : NullabilityCondition<T>

data class And<T>(
    val conditions: List<BaseCondition<T>>,
) : CompositeCondition<T> {
    constructor(vararg conditions: BaseCondition<T>) : this(conditions.toList())
}

data class Or<T>(
    val conditions: List<BaseCondition<T>>,
) : CompositeCondition<T> {
    constructor(vararg conditions: BaseCondition<T>) : this(conditions.toList())
}