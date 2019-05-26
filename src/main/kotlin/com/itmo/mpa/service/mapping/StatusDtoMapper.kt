package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Status

fun Status.toResponse() = StatusResponse(
        id,
        submittedOn,
        state = state.toResponse(),
        attributes = diseaseAttributeValues.map { it.toResponse() },
        medicines = medicines.map { it.toResponse() }
)
