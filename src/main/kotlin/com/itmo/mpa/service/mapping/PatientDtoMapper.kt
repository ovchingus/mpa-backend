package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Patient

fun PatientRequest.toEntity() = Patient().also {
    it.birthDate = birthDate!!
    it.name = name!!
}

fun Patient.toResponse(statusResponse: StatusResponse?) = PatientResponse(
        id,
        name,
        birthDate,
        statusResponse,
        disease.name
)
