package com.itmo.mpa.dto.request

import javax.validation.constraints.NotEmpty

data class AssociationRequest(

        @field:NotEmpty
        val text: String?,

        @field:NotEmpty
        val predicate: String?
)
