package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status

fun StatusRequest.toEntity(patient: Patient) = Status().also {
    it.patient = patient
}

fun Status.toResponse() = StatusResponse(id, submittedOn)
