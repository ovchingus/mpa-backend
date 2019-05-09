package com.itmo.mpa.service.impl.resolver

import com.itmo.mpa.service.impl.parsing.model.HAS_DELIMITER
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class MedicineSymbolicNameResolver(
        @Value("\${mpa.predicate.prefix.medicine}") prefix: String
) : AbstractSymbolicNameResolver(prefix) {

    private val medicineId = "id"
    private val activeSubstanceId = "activeSubstanceId"

    override fun resolveValue(parameters: ResolvingParameters, propertyName: String): String? {
        return when (propertyName) {
            medicineId -> joinId(parameters)
            activeSubstanceId -> joinSubstances(parameters)
            else -> null
        }
    }

    private fun joinId(parameters: ResolvingParameters): String {
        return parameters.draft.medicines.joinToString(HAS_DELIMITER) { it.id.toString() }
    }

    private fun joinSubstances(parameters: ResolvingParameters): String {
        return parameters.draft.medicines
                .asSequence()
                .map { it.activeSubstance }
                .flatten()
                .distinct()
                .map { it.id }
                .joinToString(HAS_DELIMITER)
    }
}
