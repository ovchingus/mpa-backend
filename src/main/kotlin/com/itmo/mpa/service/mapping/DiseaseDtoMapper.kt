package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.DiseaseRequest
import com.itmo.mpa.dto.response.DiseaseResponse
import com.itmo.mpa.entity.Disease

fun Disease.toResponse() = DiseaseResponse(
        id,
        name
)

fun DiseaseRequest.toEntity() = Disease().also {
    it.name = name!!
}