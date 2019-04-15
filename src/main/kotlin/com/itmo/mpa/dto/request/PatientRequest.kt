package com.itmo.mpa.dto.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.itmo.mpa.util.MyCustomDeserializer
import java.time.Instant
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PatientRequest(

        @field:NotEmpty
        val name: String?,

        @field:NotNull
        @JsonDeserialize(using = MyCustomDeserializer::class)
        val birthDate: Instant?,

        val diseaseId: Long?
)