package com.itmo.mpa.service.impl.parsing.model

class PredicateValue(value: String) : Comparable<PredicateValue> {

    val value = value.replace("\\s".toRegex(), "")

    /**
     *  Compares underlying String value as Double values.
     *  In case of NumberFormException it will be propagated to call site
     */
    override fun compareTo(other: PredicateValue): Int {
        return value.toDouble().compareTo(other.value.toDouble())
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PredicateValue) return false

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
