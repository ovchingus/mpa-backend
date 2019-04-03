package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.model.Draft

fun DraftRequest.toModel() = Draft(draft!!)

fun Draft.toDto() = DraftResponse(draft)
