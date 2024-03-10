package io.nikstep.conditionspec.condition

/**
 *  Base condition interface
 */
sealed interface Condition<T : Comparable<T>>

/**
 *  Condition that includes multiple other conditions
 */
sealed interface CompositeCondition<T : Comparable<T>> : Condition<T>

/**
 *  Condition that includes specific instances
 */
sealed interface InclusiveCondition<T : Comparable<T>> : Condition<T>

/**
 * Condition that excludes specific instances
 */
sealed interface ExclusiveCondition<T : Comparable<T>> : Condition<T>

/**
 *  Condition that includes instances matching like statement
 */
sealed interface LikeCondition : Condition<String>

/**
 * Condition that includes single instance
 */
sealed interface SingleCondition<T : Comparable<T>> : InclusiveCondition<T>

/**
 * Condition that includes multiple instances
 */
sealed interface MultiCondition<T : Comparable<T>> : InclusiveCondition<T>

/**
 * Condition that includes a range of instances
 */
sealed interface RangeCondition<T : Comparable<T>> : Condition<T> {
    val value: T
}

/**
 * Condition that includes instances based on their nullability
 */
sealed interface NullabilityCondition<T : Comparable<T>> : Condition<T>

/**
 * Condition that includes instances with a parameter equal to value
 */
@JvmInline
value class Eq<T : Comparable<T>>(
    val value: T,
) : SingleCondition<T>

/**
 * Condition that excludes instances with a parameter equal to value
 */
@JvmInline
value class NotEq<T : Comparable<T>>(
    val value: T,
) : ExclusiveCondition<T>

/**
 * Condition that includes instances with a parameter equal to one of values
 */
@JvmInline
value class In<T : Comparable<T>>(
    val values: List<T>,
) : MultiCondition<T> {
    constructor(vararg values: T) : this(values = values.toList())
}

/**
 * Condition that excludes instances with a parameter equal to one of values
 */
@JvmInline
value class NotIn<T : Comparable<T>>(
    val values: List<T>,
) : ExclusiveCondition<T> {
    constructor(vararg values: T) : this(values = values.toList())
}

/**
 * Condition that includes instances with a parameter greater or equal than the value
 */
@JvmInline
value class GreaterEq<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

/**
 * Condition that includes instances with a parameter greater than the value
 */
@JvmInline
value class Greater<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

/**
 * Condition that includes instances with a parameter less or equal than the value
 */
@JvmInline
value class LessEq<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

/**
 * Condition that includes instances with a parameter less than the value
 */
@JvmInline
value class Less<T : Comparable<T>>(
    override val value: T,
) : RangeCondition<T>

/**
 * Condition that includes instances with a parameter between the value1 and value2
 */
data class Between<T : Comparable<T>>(
    val value1: T,
    val value2: T,
) : MultiCondition<T> {
    constructor(vararg values: T) : this(values[0], values[1])
}

/**
 * Condition that includes instances with a parameter like the value
 */
@JvmInline
value class Like(
    val value: String,
) : LikeCondition

/**
 * Condition that includes instances with a parameter not like the value
 */
@JvmInline
value class NotLike(
    val value: String,
) : LikeCondition

/**
 * Condition that includes instances with a null parameter
 */
class Null<T : Comparable<T>> : NullabilityCondition<T>

/**
 * Condition that includes instances with a not null parameter
 */
class NotNull<T : Comparable<T>> : NullabilityCondition<T>

/**
 * Condition that includes instances with a parameter matching one of nested conditions
 */
@JvmInline
value class And<T : Comparable<T>>(
    val conditions: List<Condition<T>>,
) : CompositeCondition<T> {
    constructor(vararg conditions: Condition<T>) : this(conditions.toList())
}

/**
 * Condition that includes instances with a parameter matching all of nested conditions
 */
@JvmInline
value class Or<T : Comparable<T>>(
    val conditions: List<Condition<T>>,
) : CompositeCondition<T> {
    constructor(vararg conditions: Condition<T>) : this(conditions.toList())
}