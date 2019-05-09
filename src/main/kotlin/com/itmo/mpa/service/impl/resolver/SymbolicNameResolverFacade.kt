package com.itmo.mpa.service.impl.resolver

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SymbolicNameResolverFacade(
        patientSymbolicNameResolver: SymbolicNameResolver,
        medicineSymbolicNameResolver: SymbolicNameResolver,
        statusSymbolicNameResolver: SymbolicNameResolver
) : SymbolicNameResolver {

    private val log = LoggerFactory.getLogger(javaClass)

    private val resolvers = listOf(
            patientSymbolicNameResolver,
            medicineSymbolicNameResolver,
            statusSymbolicNameResolver
    )

    override fun resolve(parameters: ResolvingParameters, name: String): String? {
        return resolvers.asSequence()
                .mapNotNull { it.resolve(parameters, name) }
                .firstOrNull()
                .also { log.debug("Resolved {} to {}", name, it) }
    }
}
