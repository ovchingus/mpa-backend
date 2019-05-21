package com.itmo.mpa.service

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.exception.PatientNotFoundException

interface DraftService {

    /**
     *  Drops the last pending draft and use the given one for [patientId].
     *  If the draft doesn't exist saves the new one
     *
     *  @param patientId patient id
     *  @param statusDraftRequest draft of a status to be saved
     *  @throws PatientNotFoundException if patient not found
     */
    fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest)

    /**
     *  Returns current status draft by given [patientId]
     *
     *  @param patientId patient id
     *  @return found draft
     *  @throws PatientNotFoundException if patient not found
     *  @throws NoPendingDraftException if no draft is pending for a patient
     */
    fun findDraft(patientId: Long): StatusResponse
}