package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StateResponse
import com.itmo.mpa.entity.StateImage
import com.itmo.mpa.entity.states.State
import java.io.File

fun State.toResponse() = StateResponse(
        id = this.id,
        name = this.name,
        description = this.description
)
