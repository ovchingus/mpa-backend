package com.itmo.mpa.service.impl.predicate.resolver

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.itmo.mpa.entity.Patient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@Component
class PatientSymbolicNameResolver(
    @Value("\${mpa.predicate.prefix.patient}") prefix: String
) : AbstractSymbolicNameResolver(prefix) {

    private val patientAge = "age"
    private val patientDiseaseId = "diseaseId"
    private val utcZone = ZoneId.of("UTC")

    override fun resolveValue(parameters: ResolvingParameters, propertyName: String): Either<ResolvingError, String> {
        if (parameters.patient == null) throw IllegalArgumentException("parameters.patient must be set")
        return when (propertyName) {
            patientAge -> calculateAge(parameters.patient).toString().right()
            patientDiseaseId -> parameters.patient.disease.id.toString().right()
            else -> PatientResolvingError(propertyName).left()
        }
    }

    private fun calculateAge(patient: Patient): Int {
        val birthDay = patient.birthDate.atZone(utcZone).toLocalDate()
        return Period.between(birthDay, LocalDate.now(utcZone)).years
    }
}
