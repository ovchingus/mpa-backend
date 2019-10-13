package com.itmo.mpa.service.impl.entityservice

import com.itmo.mpa.entity.attributes.DiseaseAttribute
import com.itmo.mpa.entity.attributes.DiseaseAttributeValue
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.attributes.RequirementType
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.DiseaseAttributeRepository
import com.itmo.mpa.repository.DiseaseAttributeValueRepository
import com.itmo.mpa.service.exception.AttributeNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DiseaseAttributesEntityService(
        private val diseaseAttributeRepository: DiseaseAttributeRepository,
        private val diseaseAttributeValueRepository: DiseaseAttributeValueRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun replaceAttributeValues(status: Status, attributes: Map<Long, String>): Set<DiseaseAttributeValue> {
        logger.debug("replaceAttributeValues for status {} attributes: {}", status, attributes)
        val attrIdToDiseaseAttrMap = findDiseaseAttributes(attributes.keys)
        diseaseAttributeValueRepository.deleteAllByStatus(status)
        return attributes.mapTo(HashSet()) { (attributeId, attributeValue) ->
            createDiseaseAttributeValue(status, attrIdToDiseaseAttrMap.getValue(attributeId), attributeValue)
        }
    }

    fun getDiseaseAttributes(patient: Patient): List<DiseaseAttribute> {
        val attributesFromDisease = diseaseAttributeRepository
                .findByRequirementTypeAndRequirementId(RequirementType.DISEASE, patient.disease.id)
        val attributesFromState = diseaseAttributeRepository
                .findByRequirementTypeAndRequirementId(RequirementType.STATE, patient.currentStatus.state.id)

        return attributesFromDisease + attributesFromState
    }

    fun getForState(stateId: Long): List<DiseaseAttribute> {
        return diseaseAttributeRepository
                .findByRequirementTypeAndRequirementId(RequirementType.STATE, stateId)
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
        return diseaseAttributeValueRepository.save(diseaseAttributeValue)
    }
}
