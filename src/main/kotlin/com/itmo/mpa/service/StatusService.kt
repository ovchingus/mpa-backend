package com.itmo.mpa.service

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.AvailableTransitionResponse
import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.service.exception.NoCurrentStatusException
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.exception.StatusNotFoundException

interface StatusService {

    /**
     *  Creates a status for the given patient by given [patientId] using pending draft
     *  and removes a pending draft
     *
     *  @param patientId patient id
     *  @throws PatientNotFoundException if patient not found
     *  @throws NoPendingDraftException if no draft is pending for a patient
     */
    fun commitDraft(patientId: Long): StatusResponse

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

    /**
     *  Returns list of all disease attributes which could be saved within a draft
     *  and committed later on  for patient by given [patientId]
     *
     *  @param patientId patient id
     *  @return found attributes
     *  @throws PatientNotFoundException if patient not found
     *  @throws NoPendingDraftException if no draft is pending for a patient
     */
    fun getDiseaseAttributes(patientId: Long): List<DiseaseAttributeResponse>

    /**
     *  Returns list of all available transitions that are directly descendant from
     *  current state for patient by [patientId].
     *  [AvailableTransitionResponse.isRecommended] will be set according to the
     *  value produced by predicate of state's transition. It wil be set to `null` in case of any error occurred.
     *  If that's the case, [AvailableTransitionResponse.errorCause] will be populated with description
     *
     *  @param patientId patient id
     *  @return found available transitions
     *  @throws PatientNotFoundException if patient not found
     *  @throws NoPendingDraftException if no draft is pending for a patient
     */
    fun getAvailableTransitions(patientId: Long): List<AvailableTransitionResponse>

    /**
     *  Returns current status for patient by given [patientId]
     *
     *  @param patientId patient id
     *  @return current status for the patient
     *  @throws PatientNotFoundException if patient not found
     *  @throws NoCurrentStatusException if no status associated with the patient
     */
    fun findCurrentStatus(patientId: Long): StatusResponse

    /**
     *  Returns particular status for patient by given [patientId] and [statusId]
     *
     *  @param patientId patient id
     *  @param statusId status i
     *  @return current status for the patient
     *  @throws PatientNotFoundException if patient not found
     *  @throws StatusNotFoundException if no status found associated with the patient with matching [statusId]
     */
    fun findStatusById(patientId: Long, statusId: Long): StatusResponse

    /**
     *  Returns a patient history of statuses by given [patientId]
     *
     *  @param patientId patient id
     *  @return list of all statuses ever associated with the patient sorted by [StatusResponse.submittedOn]
     *  @throws PatientNotFoundException if patient not found
     */
    fun findAllForPatient(patientId: Long): List<StatusResponse>
}