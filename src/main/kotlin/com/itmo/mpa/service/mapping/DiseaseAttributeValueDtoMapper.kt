package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.DiseaseAttributeValueResponse
import com.itmo.mpa.entity.attributes.DiseaseAttributeValue

fun DiseaseAttributeValue.toResponse() = DiseaseAttributeValueResponse(
        id = this.diseaseAttribute.attribute.id,
        name = this.diseaseAttribute.attribute.name,
        value = this.value
)
