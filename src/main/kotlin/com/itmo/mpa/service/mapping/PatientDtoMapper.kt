package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Disease
import com.itmo.mpa.entity.Doctor
import com.itmo.mpa.entity.Patient

fun PatientRequest.toEntity(disease: Disease, doctor: Doctor) = Patient().also {
    it.birthDate = birthDate!!
    it.name = name!!
    it.disease = disease
    it.doctor = doctor
}

fun Patient.toResponse(statusResponse: StatusResponse?) = PatientResponse(
        id,
        name,
        birthDate,
        statusResponse,
        disease.name,
        doctor.toResponse()
)
