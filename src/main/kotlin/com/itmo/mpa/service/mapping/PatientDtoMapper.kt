package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status

fun PatientRequest.toEntity(statusEntity: Status?) = Patient().also {
    it.age = age!!
    it.name = name!!
    it.status = statusEntity
}

fun Patient.toResponse() = PatientResponse(id, name, status?.toResponse())
