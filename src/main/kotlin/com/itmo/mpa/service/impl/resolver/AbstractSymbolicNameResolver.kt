package com.itmo.mpa.service.impl.resolver

abstract class AbstractSymbolicNameResolver(
        private val referenceName: String
) : SymbolicNameResolver {

    private val referenceDelimiter = '.'

    final override fun resolve(parameters: ResolvingParameters, name: String): String? {
        if (!name.startsWith(referenceName)) {
            return null
        }
        val propertyName = name.substringAfter(referenceDelimiter, missingDelimiterValue = "")
        if (propertyName.isBlank()) {
            return null
        }
        return resolveValue(parameters, propertyName)
    }

    /**
     *  Resolves given property name
     */
    protected abstract fun resolveValue(parameters: ResolvingParameters, propertyName: String): String?
}
