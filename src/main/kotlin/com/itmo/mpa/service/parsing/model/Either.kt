package com.itmo.mpa.service.parsing.model

sealed class Either<out L : Comparable<*>, out R : Comparable<*>>

class Numerical<out T>(val value: T) : Either<T, Nothing>()
        where T : Comparable<*>, T : Number

class Textual<out T>(val value: T) : Either<Nothing, T>()
        where T : Comparable<*>, T : CharSequence
