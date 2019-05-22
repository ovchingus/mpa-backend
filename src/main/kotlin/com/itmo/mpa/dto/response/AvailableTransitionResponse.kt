package com.itmo.mpa.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

data class AvailableTransitionResponse(
        val state: StateResponse,
        val isRecommended: Boolean?,
        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val errors: List<PredicateErrorResponse>?
)