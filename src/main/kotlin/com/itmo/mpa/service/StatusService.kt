package com.itmo.mpa.service

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse

interface StatusService {

    fun commitDraft(patientId: Long)

    fun rewriteDraft(patientId: Long, draftRequest: DraftRequest)

    fun findDraft(patientId: Long) : DraftResponse?
}