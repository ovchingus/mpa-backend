package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Status

fun StatusRequest.toEntity() = Status().also {
    it.name = name!!
    it.description = description!!
}

fun Status.toResponse() = StatusResponse(id, name, description)
