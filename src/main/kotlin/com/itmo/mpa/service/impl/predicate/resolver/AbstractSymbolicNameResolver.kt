package com.itmo.mpa.service.impl.predicate.resolver

abstract class AbstractSymbolicNameResolver(
        private val referenceName: String
) : SymbolicNameResolver {

    private val referenceDelimiter = '.'

    final override fun resolve(parameters: ResolvingParameters, name: String): String {
        if (!isSupported(name)) {
            throw IllegalArgumentException("$name is not supported by this resolver")
        }
        val propertyName = name.substringAfter(referenceDelimiter, missingDelimiterValue = "")
        return resolveValue(parameters, propertyName)
    }

    override fun isSupported(name: String): Boolean {
        return name.substringBefore(referenceDelimiter) == referenceName
    }

    /**
     *  Resolves given property name
     */
    protected abstract fun resolveValue(parameters: ResolvingParameters, propertyName: String): String
}
