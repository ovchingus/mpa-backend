package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Status

fun Status.toResponse() = StatusResponse(
        id = this.id,
        submittedOn = this.submittedOn,
        state = this.state.toResponse(),
        attributes = this.diseaseAttributeValues.map { it.toResponse() },
        medicines = this.medicines.map { it.toResponse() }
)
