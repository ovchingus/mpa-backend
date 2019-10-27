package com.itmo.mpa.service.impl.predicate.resolver

import arrow.core.Either
import arrow.core.firstOrNone
import arrow.core.left
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

    fun resolve(parameters: ResolvingParameters, name: String): Either<ResolvingError, String> {
        return resolvers.firstOrNone { it.isSupported(name) }
                .fold({ NoMatchedResolverError(name).left() }) { it.resolve(parameters, name) }
                .also { log.debug("Resolved {} to {}", name, it) }
    }
}
