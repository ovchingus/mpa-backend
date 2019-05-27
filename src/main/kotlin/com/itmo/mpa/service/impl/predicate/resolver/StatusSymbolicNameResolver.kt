package com.itmo.mpa.service.impl.predicate.resolver

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StatusSymbolicNameResolver(
        @Value("\${mpa.predicate.prefix.status}") prefix: String
) : AbstractSymbolicNameResolver(prefix) {

    val stateIdProperty = "state.id"

    override fun resolveValue(parameters: ResolvingParameters, propertyName: String): String {
        return if (propertyName == stateIdProperty) {
            parameters.draft?.state?.id?.toString()
        } else {
            parameters.draft?.diseaseAttributeValues
                    ?.firstOrNull { it.diseaseAttribute.attribute.id == propertyName.toLong() }
                    ?.value
        } ?: throw ResolvingException(code = ResolverErrorCode.STATUS.code, reason = propertyName)
    }
}
