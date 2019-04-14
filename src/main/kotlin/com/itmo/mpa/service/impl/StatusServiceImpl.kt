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
        logInfo("commitDraft: create new status from draft for patient with id - $patientId")

        val (statusDraft, patient) = findDraftWithPatient(patientId)
        if (statusDraft == null) {
            logWarn("commitDraft: attempt to commit not existing draft")
            throw NoPendingDraftException(patientId)
        }
        statusDraft.draft = false
        statusRepository.save(statusDraft)
        patient.status = statusDraft
        patientRepository.save(patient)
        return statusDraft.toResponse()
    }

    override fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest) {
        logInfo("rewriteDraft: change existing draft or create one for patient with id - $patientId, " +
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
        logInfo("findDraft: find draft by patient id - $patientId")

        val (statusDraft) = findDraftWithPatient(patientId)

        logInfo("findDraft: Result: ${statusDraft?.toResponse() ?: "null"}")

        return statusDraft?.toResponse()
    }

    private fun findDraftWithPatient(patientId: Long): Pair<Status?, Patient> {
        val patient = patientRepository.findByIdOrNull(patientId)

        if (patient == null) {
            logError("findDraft: cannot find patient with id - $patientId")
            throw PatientNotFoundException(patientId)
        }

        return Pair(statusRepository.findStatusByPatientAndDraft(patient, draft = true), patient)
    }

    private fun logInfo(msg: String) {
        if (logger.isInfoEnabled) {
            logger.info("STATUS_SERVICE: $msg")
        }
    }

    private fun logWarn(msg: String) {
        if (logger.isWarnEnabled) {
            logger.warn("STATUS_SERVICE: $msg")
        }
    }

    private fun logError(msg: String) {
        if (logger.isErrorEnabled) {
            logger.error("STATUS_SERVICE: $msg")
        }
    }
}