package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.AttributeValueEnumConstantResponse
import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.entity.attributes.DiseaseAttribute

fun DiseaseAttribute.toResponse() = DiseaseAttributeResponse(
        id = this.attribute.id,
        name = this.attribute.name,
        type = this.attribute.type,
        isRequired = this.isRequired,
        requirementId = this.requirementId,
        requirementType = this.requirementType.toString().toLowerCase(),
        possibleValues = this.attribute.possibleValues.map { possibleAttrValue ->
            AttributeValueEnumConstantResponse(
                    id = possibleAttrValue.id,
                    value = possibleAttrValue.value
            )
        }.takeIf { it.isNotEmpty() }
)
