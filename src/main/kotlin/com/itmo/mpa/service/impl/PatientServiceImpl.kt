package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.PatientService
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
        private val patientRepository: PatientRepository
) : PatientService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun createPatient(patientRequest: PatientRequest) {
        logger.info("createPatient: Create a new patient from request: $patientRequest")

        val patient = patientRequest.toEntity()
        patientRepository.save(patient)
    }

    override fun findAll(): List<PatientResponse> {
        logger.info("findAll: Query all patients from the database")

        val result = patientRepository.findAll().map { it.toResponse() }

        logger.info("findAll: Result: $result")
        return result
    }

    override fun findPatient(id: Long): PatientResponse {
        logger.info("findPatient: find patient by id - $id")

        val result = patientRepository.findByIdOrNull(id)?.toResponse()

        logger.info("findPatient: result is $result")
        return result ?: throw PatientNotFoundException(id)
    }
}