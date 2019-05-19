package com.itmo.mpa.service.impl.resolver

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status

data class ResolvingParameters(
        val patient: Patient?,
        val draft: Status?
)