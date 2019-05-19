package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StateResponse
import com.itmo.mpa.entity.State

fun State.toResponse() = StateResponse(id, name, description)
