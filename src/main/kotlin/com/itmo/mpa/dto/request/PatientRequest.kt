package com.itmo.mpa.dto.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.itmo.mpa.util.InstantDeserializer
import java.time.Instant
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PatientRequest(

        @field:NotEmpty
        val name: String?,

        @field:NotNull
        @JsonDeserialize(using = InstantDeserializer::class)
        val birthDate: Instant?,

        @field:NotNull
        val diseaseId: Long?,

        @field:NotNull
        val doctorId: Long?
)