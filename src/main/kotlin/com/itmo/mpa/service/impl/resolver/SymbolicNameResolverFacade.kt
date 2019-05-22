package com.itmo.mpa.service.impl.resolver

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SymbolicNameResolverFacade(
        patientSymbolicNameResolver: SymbolicNameResolver,
        medicineSymbolicNameResolver: SymbolicNameResolver,
        statusSymbolicNameResolver: SymbolicNameResolver
) {

    private val log = LoggerFactory.getLogger(javaClass)

    private val resolvers = listOf(
            patientSymbolicNameResolver,
            medicineSymbolicNameResolver,
            statusSymbolicNameResolver
    )

    fun resolve(parameters: ResolvingParameters, name: String): String {
        val symbolicNameResolver = resolvers.asSequence()
                .firstOrNull { it.isSupported(name) }

        return symbolicNameResolver?.resolve(parameters, name)
                .also { log.debug("Resolved {} to {}", name, it) }
                ?: throw ResolvingException(code = ResolverErrorCode.UNKNOWN.code, reason = name)
    }
}
