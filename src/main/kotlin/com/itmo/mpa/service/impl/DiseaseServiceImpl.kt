package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.DiseaseRequest
import com.itmo.mpa.dto.response.DiseaseResponse
import com.itmo.mpa.repository.DiseaseRepository
import com.itmo.mpa.service.DiseaseService
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DiseaseServiceImpl(
        private val diseaseRepository: DiseaseRepository
) : DiseaseService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun createDisease(diseaseRequest: DiseaseRequest) {

        logger.info("createDisease: Create a new disease from request: $diseaseRequest")

        diseaseRepository.save(diseaseRequest.toEntity())
    }

    override fun getAll(): List<DiseaseResponse> {

        logger.info("getAll: Query all patients from the database")

        val result = diseaseRepository.findAll().map { it.toResponse() }

        logger.info("getAll: Result: $result")

        return result
    }
}