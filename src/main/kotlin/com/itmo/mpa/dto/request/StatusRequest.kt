package com.itmo.mpa.dto.request

import org.jetbrains.annotations.NotNull

data class StatusRequest(

        @field:NotNull
        val name: String?,

        @field:NotNull
        val description: String?
)