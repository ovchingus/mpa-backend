package com.itmo.mpa.service

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.dto.response.StatusResponse

interface StatusService {

    fun commitDraft(id: Int)

    fun createDraft(id: Int, draftRequest: DraftRequest)

    fun findStatus(id: Int) : StatusResponse?

    fun findStatusDraft(id: Int) : DraftResponse?
}