package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.entity.DiseaseAttribute

fun DiseaseAttribute.toResponse() = DiseaseAttributeResponse(
        name = attribute.name,
        type = attribute.type,
        isRequired = isRequired,
        requirementId = requirementId,
        requirementType = requirementType.toString().toLowerCase()
)