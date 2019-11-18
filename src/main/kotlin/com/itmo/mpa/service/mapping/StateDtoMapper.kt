package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StateResponse
import com.itmo.mpa.entity.states.State

fun State.toResponse() = StateResponse(
        id = this.id,
        name = this.name,
        description = this.description
)
