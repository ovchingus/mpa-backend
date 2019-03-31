package com.itmo.mpa.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PatientRequest(

        @field:NotEmpty
        val name: String?,

        @field:NotNull
        @field:Min(0)
        @field:Max(120)
        val age: Int?,

        @field:NotNull
        val status: StatusRequest?
)