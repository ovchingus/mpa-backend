package com.itmo.mpa.service.parsing.model

sealed class Either<L : Comparable<L>, R : Comparable<R>> : Comparable<Either<L, R>> {

    override operator fun compareTo(other: Either<L, R>): Int {
        return when (this) {
            is Numerical -> {
                when (other) {
                    is Numerical -> this.value.compareTo(other.value)
                    is Textual -> this.value.toString().compareTo(other.value.toString())
                }
            }
            is Textual -> other.compareTo(this)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Either<*, *>) return false

        return when (this) {
            is Numerical -> {
                when (other) {
                    is Numerical<*> -> this.value == other.value
                    is Textual<*> -> this.value.toString() == other.value.toString()
                }
            }
            is Textual -> other == this
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is Numerical -> this.value.hashCode()
            is Textual -> this.value.hashCode()
        }
    }
}

class Numerical<T>(val value: T) : Either<T, Nothing>()
        where T : Comparable<T>, T : Number

class Textual<T>(val value: T) : Either<Nothing, T>()
        where T : Comparable<T>, T : CharSequence
