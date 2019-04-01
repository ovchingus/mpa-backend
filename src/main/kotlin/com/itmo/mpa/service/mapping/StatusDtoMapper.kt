package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.StatusRequest
import com.itmo.mpa.entity.Status

fun StatusRequest.toEntity() = Status().let {
    it.name = name
    it.description = description
    it
}

fun Status.toDto() = StatusRequest(name, description)
