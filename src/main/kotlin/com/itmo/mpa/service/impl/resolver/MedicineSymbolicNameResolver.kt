package com.itmo.mpa.service.impl.resolver

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class MedicineSymbolicNameResolver(
        @Value("\${mpa.predicate.prefix.medicine}") prefix: String
) : AbstractSymbolicNameResolver(prefix) {

    override fun resolveValue(parameters: ResolvingParameters, propertyName: String): String? {
        return null
    }
}
