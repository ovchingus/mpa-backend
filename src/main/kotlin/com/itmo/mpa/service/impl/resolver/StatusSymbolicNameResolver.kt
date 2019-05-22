package com.itmo.mpa.service.impl.resolver

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StatusSymbolicNameResolver(
        @Value("\${mpa.predicate.prefix.status}") prefix: String
) : AbstractSymbolicNameResolver(prefix) {

    override fun resolveValue(parameters: ResolvingParameters, propertyName: String): String {
        val matchedAttribute = parameters.draft?.diseaseAttributeValues
                ?.firstOrNull { it.diseaseAttribute.attribute.id == propertyName.toLong() }
                ?: throw ResolvingException(code = ResolverErrorCode.STATUS.code, reason = propertyName)

        return matchedAttribute.value
    }
}
