package com.itmo.mpa.dto.request

data class DiseaseAttributeValueRequest(
    val id: Long, // id of an attribute, not disease_attribute or disease_attribute_value
    val value: String
)
