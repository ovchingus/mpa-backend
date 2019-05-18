package com.itmo.mpa.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.itmo.mpa.util.InstantSerializer
import java.time.Instant

data class AssociationResponse(
        val id: Long,
        val text: String,
        @JsonSerialize(using = InstantSerializer::class)
        val createdOn: Instant
)
