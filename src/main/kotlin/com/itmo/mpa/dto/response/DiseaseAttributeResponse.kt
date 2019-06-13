package com.itmo.mpa.dto.response

data class DiseaseAttributeResponse(
        val id: Long,
        val name: String,
        val type: String,
        val isRequired: Boolean,
        val requirementType: String,
        val requirementId: Long
)
