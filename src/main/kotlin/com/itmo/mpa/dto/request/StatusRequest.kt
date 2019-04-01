package com.itmo.mpa.dto.request

import org.jetbrains.annotations.NotNull

data class StatusRequest(

        @NotNull
        val name: String,

        @NotNull
        val description: String
)