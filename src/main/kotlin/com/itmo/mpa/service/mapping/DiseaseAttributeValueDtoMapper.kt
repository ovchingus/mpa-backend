package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.DiseaseAttributeValueResponse
import com.itmo.mpa.entity.DiseaseAttributeValue

fun DiseaseAttributeValue.toResponse() = DiseaseAttributeValueResponse(
        id = diseaseAttribute.attribute.id,
        name = diseaseAttribute.attribute.name,
        value = value
)
