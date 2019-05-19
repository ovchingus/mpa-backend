package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.entity.Disease
import com.itmo.mpa.entity.Doctor
import com.itmo.mpa.entity.Patient
import java.time.ZoneId
import java.time.ZoneOffset

private val utcZone = ZoneId.of("UTC")

fun PatientRequest.toEntity(disease: Disease, doctor: Doctor) = Patient().also {
    it.birthDate = birthDate!!.atStartOfDay().toInstant(ZoneOffset.UTC)
    it.name = name!!
    it.disease = disease
    it.doctor = doctor
}

fun Patient.toResponse() = PatientResponse(
        id,
        name,
        birthDate.atZone(utcZone).toLocalDate(),
        currentStatus?.toResponse(),
        disease.name,
        doctor.toResponse()
)
