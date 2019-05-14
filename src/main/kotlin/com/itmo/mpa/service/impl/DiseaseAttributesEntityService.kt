package com.itmo.mpa.service.impl

import com.itmo.mpa.entity.DiseaseAttribute
import com.itmo.mpa.entity.DiseaseAttributeValue
import com.itmo.mpa.entity.RequirementType
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.DiseaseAttributeRepository
import com.itmo.mpa.repository.DiseaseAttributeValueRepository
import com.itmo.mpa.service.exception.AttributeNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DiseaseAttributesEntityService(
        private val diseaseAttributeRepository: DiseaseAttributeRepository,
        private val diseaseAttributeValueRepository: DiseaseAttributeValueRepository,
        private val patientStatusEntityService: PatientStatusEntityService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun replaceAttributeValues(status: Status, attributes: Map<Long, String>): Set<DiseaseAttributeValue> {
        val attrIdToDiseaseAttrMap = findDiseaseAttributes(attributes.keys)
        diseaseAttributeValueRepository.deleteALlByStatus(status)
        return attributes.mapTo(HashSet()) { (attributeId, attributeValue) ->
            createDiseaseAttributeValue(status, attrIdToDiseaseAttrMap.getValue(attributeId), attributeValue)
        }.also { logger.debug("replaceAttributeValues for status {} result: {}", status, it) }
    }

    fun getDiseaseAttributes(patientId: Long): List<DiseaseAttribute> {
        val (status, patient) = patientStatusEntityService.requireDraftWithPatient(patientId)

        val attributesFromDisease = diseaseAttributeRepository
                .findByRequirementTypeAndRequirementId(RequirementType.DISEASE, patient.disease.id)
        val attributesFromState = diseaseAttributeRepository
                .findByRequirementTypeAndRequirementId(RequirementType.STATE, status.state.id)

        return attributesFromDisease + attributesFromState
    }

    private fun findDiseaseAttributes(requestAttributeIds: Set<Long>): Map<Long, DiseaseAttribute> {
        val attrIdToDiseaseAttrMap = diseaseAttributeRepository.findAllByAttributeIdIn(requestAttributeIds)
                .groupBy { disAttr -> disAttr.attribute.id }
                .mapValues { (_, diseaseAttributeList) -> diseaseAttributeList.first() }

        if (requestAttributeIds.size != attrIdToDiseaseAttrMap.size) {
            val notFoundIds: Set<Long> = requestAttributeIds - attrIdToDiseaseAttrMap.keys
            throw AttributeNotFoundException(notFoundIds)
        }
        return attrIdToDiseaseAttrMap
    }

    private fun createDiseaseAttributeValue(
            status: Status,
            diseaseAttribute: DiseaseAttribute,
            attributeValue: String
    ): DiseaseAttributeValue {
        val diseaseAttributeValue = DiseaseAttributeValue().apply {
            this.status = status
            value = attributeValue
            this.diseaseAttribute = diseaseAttribute
        }
        diseaseAttributeValueRepository.save(diseaseAttributeValue)
        return diseaseAttributeValue
    }
}
