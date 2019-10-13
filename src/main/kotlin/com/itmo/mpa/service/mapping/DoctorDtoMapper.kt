package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.DoctorRequest
import com.itmo.mpa.dto.response.DoctorResponse
import com.itmo.mpa.entity.Doctor

fun DoctorRequest.toEntity() = Doctor().also {
    it.name = this.name!!
}

fun Doctor.toResponse() = DoctorResponse(
        id = this.id,
        name = this.name
)
