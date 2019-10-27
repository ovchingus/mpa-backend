package com.itmo.mpa.service.impl.predicate.resolver

import arrow.core.Either
import arrow.core.left

abstract class AbstractSymbolicNameResolver(
    private val referenceName: String
) : SymbolicNameResolver {

    private val referenceDelimiter = '.'

    /**
     *  Resolves given reference to a [String].
     *
     *  Note: call this method only if the name is acceptable by [isSupported] method.
     *  Otherwise, the result could be unpredictable.
     */
    final override fun resolve(parameters: ResolvingParameters, name: String): Either<ResolvingError, String> {
        if (!isSupported(name)) return IllegalResolvingStateError.left()
        val propertyName = name.substringAfter(referenceDelimiter, missingDelimiterValue = "")
        return resolveValue(parameters, propertyName)
    }

    override fun isSupported(name: String): Boolean {
        return name.substringBefore(referenceDelimiter) == referenceName
    }

    /**
     *  Resolves given property name
     */
    protected abstract fun resolveValue(
        parameters: ResolvingParameters,
        propertyName: String
    ): Either<ResolvingError, String>
}
