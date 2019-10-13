package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.entity.attributes.DiseaseAttribute

fun DiseaseAttribute.toResponse() = DiseaseAttributeResponse(
        id = attribute.id,
        name = attribute.name,
        type = attribute.type,
        isRequired = isRequired,
        requirementId = requirementId,
        requirementType = requirementType.toString().toLowerCase()
)
