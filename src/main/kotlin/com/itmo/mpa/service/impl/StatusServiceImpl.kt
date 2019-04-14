package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.entity.Draft
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.DraftRepository
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

@Service
class StatusServiceImpl(
        private val patientRepository: PatientRepository,
        private val draftRepository: DraftRepository,
        private val statusRepository: StatusRepository
) : StatusService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun commitDraft(patientId: Long) {
        logInfo("commitDraft: create new status from draft for patient with id - $patientId")

        val (draft, patient) = findDraftWithPatient(patientId)
        if (draft == null) {
            logWarn("commitDraft: attempt to commit not existing draft")
            throw NoPendingDraftException(patientId)
        }
        val status = Status().apply {
            name = draft.name
            description = draft.description
        }
        draftRepository.delete(draft)
        status.patient = patient
        statusRepository.save(status)
        patient.status = status
        patientRepository.save(patient)
    }

    override fun rewriteDraft(patientId: Long, draftRequest: DraftRequest) {
        logInfo("rewriteDraft: change existing draft or create one for patient with id - $patientId, " +
                "by draft request - $draftRequest")

        val (oldDraft, patient) = findDraftWithPatient(patientId)
        if (oldDraft != null) {
            draftRepository.delete(oldDraft)
        }
        draftRepository.save(draftRequest.toEntity(patient))
    }

    override fun findDraft(patientId: Long): DraftResponse? {
        logInfo("findDraft: find draft by patient id - $patientId")
        val (draft) = findDraftWithPatient(patientId)

        logInfo("findDraft: Result: ${draft?.toResponse() ?: "null"}")

        return draft?.toResponse()
    }

    private fun findDraftWithPatient(patientId: Long): Pair<Draft?, Patient> {
        val patient = patientRepository.findByIdOrNull(patientId)

        if (patient == null) {
            logError("findDraft: cannot find patient with id - $patientId")
            throw PatientNotFoundException(patientId)
        }

        return Pair(draftRepository.findDraftByPatient(patient), patient)
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