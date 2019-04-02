package com.itmo.mpa.dto.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class StatusRequest(

        @field:NotEmpty
        val status: String,


        @field:NotNull
        val draft: DraftRequest?
)