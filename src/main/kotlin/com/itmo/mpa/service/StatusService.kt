package com.itmo.mpa.service

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse

interface StatusService {

    /**
     *  Creates a status for the given patient by given [patientId] using pending draft
     *  and removes a pending draft
     *  @throws NotFoundException if patient not found
     *  @throws NoPendingDraftException if no draft is pending for a patient
     */
    fun commitDraft(patientId: Long)

    /**
     *  Drops the last pending draft and use the given one for [patientId].
     *  If the draft doesn't exist saves the new one
     *  @throws NotFoundException if patient not found
     */
    fun rewriteDraft(patientId: Long, draftRequest: DraftRequest)

    /**
     *  Returns current draft draft by given [patientId]
     *  @throws NotFoundException if patient not found
     */
    fun findDraft(patientId: Long): DraftResponse?
}