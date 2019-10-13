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
    it.birthDate = this.birthDate!!.atStartOfDay().toInstant(ZoneOffset.UTC)
    it.name = this.name!!
    it.disease = disease
    it.doctor = doctor
}

fun Patient.toResponse() = PatientResponse(
        id = this.id,
        name = this.name,
        birthDate = this.birthDate.atZone(utcZone).toLocalDate(),
        status = this.currentStatus.toResponse(),
        diseaseName = this.disease.name,
        doctor = this.doctor.toResponse()
)
