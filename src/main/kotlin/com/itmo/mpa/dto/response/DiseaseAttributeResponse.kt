package com.itmo.mpa.dto.response

data class DiseaseAttributeResponse(
        val name: String,
        val type: String,
        val isRequired: Boolean,
        val requirementType: String,
        val requirementId: Long
)