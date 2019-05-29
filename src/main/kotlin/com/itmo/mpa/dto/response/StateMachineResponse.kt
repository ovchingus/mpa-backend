package com.itmo.mpa.dto.response

data class StateMachineResponse(
        val states: List<StateResponse>,
        val transitions: List<StateTransitionResponse>
)