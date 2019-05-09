package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.*
import com.itmo.mpa.repository.*
import com.itmo.mpa.service.DraftService
import com.itmo.mpa.service.exception.AttributeNotFoundException
import com.itmo.mpa.service.exception.StateNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DraftServiceImpl(
        private val statusRepository: StatusRepository,
        private val diseaseAttributeRepository: DiseaseAttributeRepository,
        private val diseaseAttributeValueRepository: DiseaseAttributeValueRepository,
        private val attributeRepository: AttributeRepository,
        private val stateRepository: StateRepository,
        private val patientStatusEntityService: PatientStatusEntityService
) : DraftService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest) {
        logger.info("rewriteDraft: change existing draft or create one for patient with id - $patientId, " +
                "by status draft request - {}", statusDraftRequest)

        val (oldDraft, patient) = patientStatusEntityService.findDraftWithPatient(patientId)

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
        return patientStatusEntityService.requireDraftWithPatient(patientId).first
                .toResponse()
                .also { logger.info("findDraft: result {}", it) }
    }

    override fun getDiseaseAttributes(patientId: Long): List<DiseaseAttributeResponse> {
        val (status, patient) = patientStatusEntityService.requireDraftWithPatient(patientId)

        val attributesFromDisease = diseaseAttributeRepository
                .findByRequirementTypeAndRequirementId(RequirementType.DISEASE, patient.disease.id)
        val attributesFromState = diseaseAttributeRepository
                .findByRequirementTypeAndRequirementId(RequirementType.STATE, status.state.id)

        return (attributesFromDisease + attributesFromState)
                .map { it.toResponse() }
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

    private fun findDiseaseAttribute(
            attribute: Attribute,
            type: RequirementType,
            id: Long
    ): DiseaseAttribute? {
        return diseaseAttributeRepository.findByAttributeAndRequirementTypeAndRequirementId(attribute, type, id)
    }
}