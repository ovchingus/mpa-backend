package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.NoPendingDraftException
import com.itmo.mpa.service.PatientNotFoundException
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class StatusServiceImpl(
        private val patientRepository: PatientRepository,
        private val statusRepository: StatusRepository
) : StatusService {

    override fun commitDraft(patientId: Long): StatusResponse {
        val (statusDraft, patient) = findDraftWithPatient(patientId)
        if (statusDraft == null) throw NoPendingDraftException(patientId)
        statusDraft.draft = false
        statusRepository.save(statusDraft)
        patient.status = statusDraft
        patientRepository.save(patient)
        return statusDraft.toResponse()
    }

    override fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest) {
        val (oldDraft, patient) = findDraftWithPatient(patientId)
        if (oldDraft != null) {
            statusRepository.delete(oldDraft)
        }
        val status = statusDraftRequest.toEntity(patient)
        status.submittedOn = Instant.now()
        statusRepository.save(status)
    }

    override fun findDraft(patientId: Long): StatusResponse? {
        val (statusDraft) = findDraftWithPatient(patientId)
        return statusDraft?.toResponse()
    }

    private fun findDraftWithPatient(patientId: Long): Pair<Status?, Patient> {
        val patient = patientRepository.findByIdOrNull(patientId)
                ?: throw PatientNotFoundException(patientId)
        return Pair(statusRepository.findStatusByPatientAndDraft(patient, draft = true), patient)
    }
}