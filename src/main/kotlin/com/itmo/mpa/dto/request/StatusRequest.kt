package com.itmo.mpa.dto.request

import javax.validation.constraints.NotEmpty

data class StatusRequest(

        @field:NotEmpty
        val status: String
)