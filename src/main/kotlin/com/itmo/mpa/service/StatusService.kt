package com.itmo.mpa.service

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