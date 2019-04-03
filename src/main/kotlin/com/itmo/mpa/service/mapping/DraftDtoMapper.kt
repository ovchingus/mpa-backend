package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.entity.Draft
import com.itmo.mpa.entity.Patient

fun DraftRequest.toEntity(patient: Patient) = Draft().also {
    it.name = name!!
    it.description = description!!
    it.patient = patient
}

fun Draft.toResponse() = DraftResponse(name, description)
