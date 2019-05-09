package com.itmo.mpa.service.impl.resolver

import org.springframework.stereotype.Component

@Component
class SymbolicNameResolverFacade(
        patientSymbolicNameResolver: SymbolicNameResolver,
        medicineSymbolicNameResolver: SymbolicNameResolver,
        statusSymbolicNameResolver: SymbolicNameResolver
) : SymbolicNameResolver {

    private val resolvers = listOf(
            patientSymbolicNameResolver,
            medicineSymbolicNameResolver,
            statusSymbolicNameResolver
    )

    override fun resolve(parameters: ResolvingParameters, name: String): String? {
        return resolvers.asSequence()
                .mapNotNull { it.resolve(parameters, name) }
                .firstOrNull()
    }
}
