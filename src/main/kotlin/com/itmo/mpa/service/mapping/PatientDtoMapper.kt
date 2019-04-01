package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.entity.Patient

fun PatientRequest.toModel() = Patient().let {
    it.age = age!!
    it.name = name!!
    it.status = status
}

fun Patient.toDto() = PatientResponse(0, name)