package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status

fun PatientRequest.toEntity(statusEntity: Status? = null) = Patient().also {
    it.birthDate = birthDate!!
    it.name = name!!
    it.status = statusEntity
}

fun Patient.toResponse() = PatientResponse(
        id,
        name,
        birthDate,
        status?.toResponse(),
        disease?.name
)
