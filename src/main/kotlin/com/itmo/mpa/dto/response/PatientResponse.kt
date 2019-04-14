package com.itmo.mpa.dto.response

import java.time.Instant

data class PatientResponse(
        val id: Long,
        val name: String,
        // TODO: fix formatting
        val birthDate: Instant,
        val status: StatusResponse?
)