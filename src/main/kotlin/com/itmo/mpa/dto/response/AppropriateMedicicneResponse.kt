package com.itmo.mpa.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

data class AppropriateMedicicneResponse(
        val state: StateResponse,
        val isRecommended: Boolean?,
        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val errorCause: String?
)