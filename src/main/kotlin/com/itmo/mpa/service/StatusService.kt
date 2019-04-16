package com.itmo.mpa.service

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.exception.NotFoundException

interface StatusService {

    /**
     *  Creates a status for the given patient by given [patientId] using pending draft
     *  and removes a pending draft
     *
     *  @param patientId patient id
     *  @throws NotFoundException if patient not found
     *  @throws NoPendingDraftException if no draft is pending for a patient
     */
    fun commitDraft(patientId: Long): StatusResponse

    /**
     *  Drops the last pending draft and use the given one for [patientId].
     *  If the draft doesn't exist saves the new one
     *
     *  @param patientId patient id
     *  @param statusDraftRequest draft of a status to be saved
     *  @throws NotFoundException if patient not found
     */
    fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest)

    /**
     *  Returns current status draft draft by given [patientId]
     *
     *  @param patientId patient id
     *  @return found draft
     *  @throws NotFoundException if patient not found
     *  @throws NoPendingDraftException if no draft is pending for a patient
     */
    fun findDraft(patientId: Long): StatusResponse
}