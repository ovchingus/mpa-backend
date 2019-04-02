package com.itmo.mpa.service

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.dto.response.StatusResponse

interface StatusService {

    fun createStatus(statusRequest: StatusRequest)

    fun findStatus(id: Int) : StatusResponse?

    fun findStatusDraft(id: Int) : DraftResponse?
}