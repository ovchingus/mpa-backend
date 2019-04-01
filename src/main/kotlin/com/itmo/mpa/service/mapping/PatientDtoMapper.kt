package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.entity.Patient

fun PatientRequest.toEntity() = Patient().let {
    it.age = age!!
    it.name = name!!
    it.status = status
    it
}

fun Patient.toDto() = PatientResponse(id, name, null)
