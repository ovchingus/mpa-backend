package com.itmo.mpa.dto.request

import javax.validation.constraints.NotEmpty

data class DoctorRequest(

    @field:NotEmpty
    val name: String?
)
