package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.State
import com.itmo.mpa.entity.Status

fun StatusRequest.toEntity(patient: Patient, state: State) = Status().also {
    it.patient = patient
    it.state = state
}

fun Status.toResponse() = StatusResponse(
        id,
        submittedOn,
        stateId = state.id,
        attributes = diseaseAttributeValues.map { it.diseaseAttribute.attribute.name to it.value }.toMap(HashMap()),
        medicines = medicines.map { it.toResponse() }
)
