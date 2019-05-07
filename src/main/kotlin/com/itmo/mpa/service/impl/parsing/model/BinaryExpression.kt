package com.itmo.mpa.service.impl.parsing.model


sealed class BinaryExpression<T : Comparable<T>>

class Equal<T : Comparable<T>>(val left: Value<T>, val right: Value<T>) : BinaryExpression<T>()
class LessThan<T : Comparable<T>>(val left: Value<T>, val right: Value<T>) : BinaryExpression<T>()
class GreaterThan<T : Comparable<T>>(val left: Value<T>, val right: Value<T>) : BinaryExpression<T>()

class Or<T : Comparable<T>>(val left: BinaryExpression<T>, val right: BinaryExpression<T>) : BinaryExpression<T>()
class Not<T : Comparable<T>>(val other: BinaryExpression<T>) : BinaryExpression<T>()
class And<T : Comparable<T>>(val left: BinaryExpression<T>, val right: BinaryExpression<T>) : BinaryExpression<T>()

class Evaluated<T : Comparable<T>>(val value: Boolean) : BinaryExpression<T>()

sealed class Value<T>
class UnknownValue<T : Comparable<T>>(val symbolicName: String) : Value<T>()
class KnownValue<T : Comparable<T>>(val value: T) : Value<T>()

fun <T : Comparable<T>> BinaryExpression<T>.evaluate(parameters: List<T>): Boolean {
    return evaluate { symbolicName ->
        runCatching {
            parameters[symbolicName.toInt()]
        }.getOrElse {
            throw ArgumentsCountMismatchException(parameters, it)
        }
    }
}

fun <T : Comparable<T>> BinaryExpression<T>.evaluate(resolver: (String) -> T): Boolean {
    return when (this) {
        is Equal -> left.resolve(resolver) == right.resolve(resolver)
        is LessThan -> left.resolve(resolver) < right.resolve(resolver)
        is GreaterThan -> left.resolve(resolver) > right.resolve(resolver)
        is Not -> !other.evaluate(resolver)
        is Or -> left.evaluate(resolver) || right.evaluate(resolver)
        is And -> left.evaluate(resolver) && right.evaluate(resolver)
        is Evaluated -> value
    }
}

private fun <T : Comparable<T>> Value<T>.resolve(resolver: (String) -> T): T {
    return when (this) {
        is KnownValue -> value
        is UnknownValue -> resolver(symbolicName)
    }
}
