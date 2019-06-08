package com.itmo.mpa.service.impl.predicate.resolver

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StatusSymbolicNameResolver(
        @Value("\${mpa.predicate.prefix.status}") prefix: String
) : AbstractSymbolicNameResolver(prefix) {

    companion object {

        private const val stateIdProperty = "state.id"
    }

    override fun resolveValue(parameters: ResolvingParameters, propertyName: String): Either<ResolvingError, String> {
        return when (propertyName) {
            stateIdProperty -> byStateId(parameters)
            else -> byAttribute(parameters, propertyName)
        }.let { resolved ->
            resolved?.right() ?: StatusResolvingError(propertyName).left()
        }
    }

    private fun byAttribute(params: ResolvingParameters, propertyName: String): String? {
        return params.draft
                ?.diseaseAttributeValues
                ?.firstOrNull { attr -> attr.diseaseAttribute.attribute.id == propertyName.toLong() }
                ?.value
    }

    private fun byStateId(params: ResolvingParameters): String? {
        return params.draft?.state?.id?.toString()
    }
}
