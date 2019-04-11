package com.itmo.mpa.dto.request

import java.time.Instant
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PatientRequest(

        @field:NotEmpty
        val name: String?,

        // TODO: fix formatting
        @field:NotNull
        val birthDate: Instant?
)