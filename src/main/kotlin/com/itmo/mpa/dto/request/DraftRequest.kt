package com.itmo.mpa.dto.request

import org.jetbrains.annotations.NotNull

data class DraftRequest(

        @field:NotNull
        val name: String?,

        @field:NotNull
        val description: String?
)