package com.itmo.mpa.service.parsing.model


sealed class BinaryExpression<T : Comparable<T>>

class Equal<T : Comparable<T>>(val left: Value<T>, val right: Value<T>) : BinaryExpression<T>()
class LessThan<T : Comparable<T>>(val left: Value<T>, val right: Value<T>) : BinaryExpression<T>()
class GreaterThan<T : Comparable<T>>(val left: Value<T>, val right: Value<T>) : BinaryExpression<T>()

class Or<T : Comparable<T>>(val left: BinaryExpression<T>, val right: BinaryExpression<T>) : BinaryExpression<T>()
class Not<T : Comparable<T>>(val other: BinaryExpression<T>) : BinaryExpression<T>()
class And<T : Comparable<T>>(val left: BinaryExpression<T>, val right: BinaryExpression<T>) : BinaryExpression<T>()

class Evaluated<T : Comparable<T>>(val value: Boolean) : BinaryExpression<T>()

sealed class Value<T>
class UnknownValue<T : Comparable<T>>(val number: Int) : Value<T>()
class KnownValue<T : Comparable<T>>(val value: T) : Value<T>()
