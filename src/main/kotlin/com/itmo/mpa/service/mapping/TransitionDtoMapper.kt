package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StateTransitionResponse
import com.itmo.mpa.entity.states.Transition

fun Transition.toResponse() = StateTransitionResponse(
        from = this.stateFrom.id,
        to = this.stateTo.id
)
