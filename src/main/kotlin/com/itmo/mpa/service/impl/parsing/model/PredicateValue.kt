package com.itmo.mpa.service.impl.parsing.model

data class PredicateValue(val value: String) : Comparable<PredicateValue> {

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
}
