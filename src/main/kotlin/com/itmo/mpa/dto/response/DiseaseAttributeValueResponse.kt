package com.itmo.mpa.dto.response

data class DiseaseAttributeValueResponse(
        val id: Long, // id of an attribute, not disease_attribute or disease_attribute_value
        val name: String,
        val value: String
)
