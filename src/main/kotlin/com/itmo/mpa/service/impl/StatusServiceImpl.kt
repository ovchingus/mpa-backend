package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class StatusServiceImpl(
        private val patientRepository: PatientRepository,
        private val statusRepository: StatusRepository
) : StatusService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun commitDraft(patientId: Long): StatusResponse {
        logger.info("commitDraft: create new status from draft for patient with id - $patientId")

        val (statusDraft, patient) = findDraftWithPatient(patientId)
        if (statusDraft == null) {
            logger.warn("commitDraft: attempt to commit not existing draft")
            throw NoPendingDraftException(patientId)
        }

        statusDraft.draft = false
        statusRepository.save(statusDraft)
        patient.status = statusDraft
        patientRepository.save(patient)
        return statusDraft.toResponse()
    }

    override fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest) {
        logger.info("rewriteDraft: change existing draft or create one for patient with id - $patientId, " +
                "by status draft request - $statusDraftRequest")

        val (oldDraft, patient) = findDraftWithPatient(patientId)
        if (oldDraft != null) {
            statusRepository.delete(oldDraft)
        }
        val status = statusDraftRequest.toEntity(patient)
        status.submittedOn = Instant.now()
        statusRepository.save(status)
    }

    override fun findDraft(patientId: Long): StatusResponse? {
        logger.info("findDraft: find draft by patient id - $patientId")

        val (statusDraft) = findDraftWithPatient(patientId)

        logger.info("findDraft: Result: ${statusDraft?.toResponse() ?: "null"}")

        return statusDraft?.toResponse()
    }

    private fun findDraftWithPatient(patientId: Long): Pair<Status?, Patient> {
        val patient = patientRepository.findByIdOrNull(patientId)

        if (patient == null) {
            logger.error("findDraft: cannot find patient with id - $patientId")
            throw PatientNotFoundException(patientId)
        }

        return Pair(statusRepository.findStatusByPatientAndDraft(patient, draft = true), patient)
    }
}