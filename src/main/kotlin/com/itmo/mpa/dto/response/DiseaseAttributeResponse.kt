package com.itmo.mpa.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

data class DiseaseAttributeResponse(
        val id: Long,
        val name: String,
        val type: String,
        val isRequired: Boolean,
        val requirementType: String,
        val requirementId: Long,
        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val possibleValues: List<AttributeValueEnumConstantResponse>?
)
