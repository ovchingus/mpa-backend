package com.itmo.mpa.controller.request

import javax.validation.constraints.NotEmpty

data class StatusRequest(

        @field:NotEmpty
        val status: String
)