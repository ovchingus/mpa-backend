package com.itmo.mpa.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PatientRequest(

    @field:NotEmpty
    val name: String?,

    @field:NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate?,

    @field:NotNull
    val diseaseId: Long?,

    @field:NotNull
    val doctorId: Long?
)
