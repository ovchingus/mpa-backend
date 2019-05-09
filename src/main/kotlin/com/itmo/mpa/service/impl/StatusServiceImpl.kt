package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.*
import com.itmo.mpa.repository.*
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.*
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class StatusServiceImpl(
        private val patientRepository: PatientRepository,
        private val statusRepository: StatusRepository,
        private val diseaseAttributeRepository: DiseaseAttributeRepository,
        private val diseaseAttributeValueRepository: DiseaseAttributeValueRepository,
        private val attributeRepository: AttributeRepository,
        private val stateRepository: StateRepository
) : StatusService {

    private val logger = LoggerFactory.getLogger(javaClass)

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

        val state = stateRepository.findByIdOrNull(statusDraftRequest.stateId!!)
                ?: throw StateNotFoundException(statusDraftRequest.stateId)

        var statusEntity = oldDraft ?: statusDraftRequest.toEntity(patient, state)
        statusEntity.submittedOn = Instant.now()

        statusEntity = statusRepository.save(statusEntity)

        val savedAttributeValues = statusDraftRequest.attributes
                .map { (attributeName, attributeValue) ->
                    DiseaseAttributeValue().apply {
                        status = statusEntity
                        diseaseAttribute = findAttribute(statusEntity, attributeName)
                        value = attributeValue
                    }
                }.let { diseaseAttributeValueRepository.saveAll(it) }

        statusEntity.diseaseAttributeValues = savedAttributeValues.toSet() + statusEntity.diseaseAttributeValues

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
                ?: throw NoCurrentStatusException(patientId)

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

    private fun findAttribute(statusEntity: Status, attributeName: String): DiseaseAttribute {

        val attribute = attributeRepository.findByName(attributeName)
                ?: throw AttributeNotFoundException(attributeName)

        val (state, disease) = statusEntity.state to statusEntity.patient.disease

        val attributeForStateDisease = findDiseaseAttribute(attribute, RequirementType.DISEASE, disease.id)
        if (attributeForStateDisease != null) return attributeForStateDisease

        return findDiseaseAttribute(attribute, RequirementType.STATE, state.id)
                ?: throw AttributeNotFoundException(attributeName)
    }

    fun findDiseaseAttribute(
            attribute: Attribute,
            type: RequirementType,
            id: Long
    ): DiseaseAttribute? {
        return diseaseAttributeRepository.findByAttributeAndRequirementTypeAndRequirementId(attribute, type, id)
    }
}