package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.DiseaseAttribute
import com.itmo.mpa.entity.DiseaseAttributeValue
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.exception.StatusNotFoundException
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

        val statusDraft = statusRepository.findStatusByPatientIdAndDraft(patientId, draft = true)
                ?: throw NoPendingDraftException(patientId)

        statusDraft.draft = false
        statusRepository.save(statusDraft)
        return statusDraft.toResponse()
    }

    override fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest) {
        logger.info("rewriteDraft: change existing draft or create one for patient with id - $patientId, " +
                "by status draft request - {}", statusDraftRequest)

        val (oldDraft, patient) = findDraftWithPatient(patientId)

        var statusEntity = oldDraft ?: statusDraftRequest.toEntity(patient)
        statusEntity.submittedOn = Instant.now()
        // todo: save and check stateId from the request

        statusEntity = statusRepository.save(statusEntity)

        statusEntity.diseaseAttributeValues = statusDraftRequest.attributes.map { (attributeName, attributeValue) ->
            DiseaseAttributeValue().apply {
                status = statusEntity
                diseaseAttribute = findAttribute(statusEntity, attributeName, attributeValue)
            }
        }.toSet() + statusEntity.diseaseAttributeValues

        statusRepository.save(statusEntity)
    }

    override fun findDraft(patientId: Long): StatusResponse {
        logger.info("findDraft: find draft by patient id - $patientId")

        val (statusDraft) = findDraftWithPatient(patientId)

        logger.info("findDraft: Result: ${statusDraft?.toResponse() ?: "null"}")

        return statusDraft?.toResponse() ?: throw NoPendingDraftException(patientId)
    }

    override fun findCurrentStatus(patientId: Long): StatusResponse {
        logger.info("findCurrentStatus: patientId {}", patientId)
        val status = statusRepository.findStatusByPatientIdAndDraft(patientId, draft = false)
                ?: throw NoPendingDraftException(patientId)

        return status.toResponse()
    }

    override fun findStatusById(patientId: Long, statusId: Long): StatusResponse {
        logger.info("findStatusById: patientId {}, statusId {}", patientId, statusId)
        val patient = findPatient(patientId)
        val status = statusRepository.findStatusByPatientAndId(patient, statusId)
                ?: throw StatusNotFoundException(patientId, statusId)
        return status.toResponse()
    }

    override fun findAllForPatient(patientId: Long): List<StatusResponse> {
        logger.info("findAllForPatient: {}", patientId)
        val patient = findPatient(patientId)
        return statusRepository.findStatusesByPatientOrderBySubmittedOnAsc(patient)
                .map { it.toResponse() }
    }

    private fun findPatient(patientId: Long): Patient {
        logger.info("findPatient: {}", patientId)
        return patientRepository.findByIdOrNull(patientId) ?: throw PatientNotFoundException(patientId)
    }

    private fun findDraftWithPatient(patientId: Long): Pair<Status?, Patient> {
        val patient = patientRepository.findByIdOrNull(patientId) ?: throw PatientNotFoundException(patientId)
        return Pair(statusRepository.findStatusByPatientAndDraft(patient, draft = true), patient)
    }

    private fun findAttribute(statusEntity: Status, attributeName: String, attributeValue: String): DiseaseAttribute {
        TODO(" Go to AttributeService and find if any ")
    }
}