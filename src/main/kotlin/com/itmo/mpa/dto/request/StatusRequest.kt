package com.itmo.mpa.dto.request

import javax.validation.constraints.NotNull

data class StatusRequest(

        @field:NotNull
        val stateId: Long?,

        val medicines: List<Long> = emptyList(),

        val attributes: Map<String, String> = emptyMap()
)