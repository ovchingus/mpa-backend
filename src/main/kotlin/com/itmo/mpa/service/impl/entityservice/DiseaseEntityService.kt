package com.itmo.mpa.service.impl.entityservice

import com.itmo.mpa.entity.Disease
import com.itmo.mpa.repository.DiseaseRepository
import com.itmo.mpa.service.exception.DiseaseNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DiseaseEntityService(
    private val diseaseRepository: DiseaseRepository
) {

    fun findDisease(diseaseId: Long): Disease {
        return diseaseRepository.findByIdOrNull(diseaseId)
                ?: throw DiseaseNotFoundException(diseaseId)
    }
}
