package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.StatusDto
import com.itmo.mpa.entity.Status

fun StatusDto.toEntity() = Status().let {
    it.name = name
    it.description = description
    it
}

fun Status.toDto() = StatusDto(name, description)
