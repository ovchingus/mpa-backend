package com.itmo.mpa.dto.request

import javax.validation.constraints.NotEmpty

data class DraftRequest(

        @field:NotEmpty
        val draft: String?
)