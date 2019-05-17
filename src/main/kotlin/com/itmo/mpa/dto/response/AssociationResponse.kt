package com.itmo.mpa.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.itmo.mpa.util.InstantSerializer
import java.time.Instant

data class AssociationResponse(
        val id: Long,
        val text: String,
        @JsonSerialize(using = InstantSerializer::class)
        val createdOn: Instant,
        val isRelevant: Boolean?,
        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val errorCause: String?
)
