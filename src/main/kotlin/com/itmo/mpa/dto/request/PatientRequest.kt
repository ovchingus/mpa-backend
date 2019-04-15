package com.itmo.mpa.dto.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.itmo.mpa.util.MyCustomDeserializer
import com.itmo.mpa.util.MyCustomSerializer
import java.time.Instant
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PatientRequest(

        @field:NotEmpty
        val name: String?,

        @field:NotNull
        @JsonDeserialize(using = MyCustomDeserializer::class)
        @JsonSerialize(using = MyCustomSerializer::class)
        val birthDate: Instant?,

        val diseaseId: Long?
)