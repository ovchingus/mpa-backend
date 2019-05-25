package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.service.AttributeService
import com.itmo.mpa.service.impl.entityservice.DiseaseAttributesEntityService
import com.itmo.mpa.service.impl.entityservice.PatientStatusEntityService
import com.itmo.mpa.service.mapping.toResponse
import org.springframework.stereotype.Service

@Service
class AttributeServiceImpl(
        private val attributesEntityService: DiseaseAttributesEntityService,
        private val patientStatusEntityService: PatientStatusEntityService
) : AttributeService {

    override fun getDiseaseAttributes(patientId: Long): List<DiseaseAttributeResponse> {
        val patient = patientStatusEntityService.findPatient(patientId)
        return attributesEntityService.getDiseaseAttributes(patient)
                .map { it.toResponse() }
    }

    override fun getForState(stateId: Long): List<DiseaseAttributeResponse> {
        return attributesEntityService.getForState(stateId)
                .map { it.toResponse() }
    }
}
