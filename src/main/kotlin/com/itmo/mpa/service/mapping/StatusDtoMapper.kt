package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Status

fun Status.toResponse() = StatusResponse(
        id,
        submittedOn,
        state = state.toResponse(),
        attributes = diseaseAttributeValues.map { it.diseaseAttribute.attribute.name to it.value }.toMap(HashMap()),
        medicines = medicines.map { it.toResponse() }
)
