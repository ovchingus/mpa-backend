package com.itmo.mpa.dto.response

import java.time.Instant

data class StatusResponse(
        val id: Long,
        val submittedOn: Instant
)