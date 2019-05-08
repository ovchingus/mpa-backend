package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.PatientService
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
        private val patientRepository: PatientRepository,
        private val statusService: StatusService
) : PatientService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun createPatient(patientRequest: PatientRequest) {
        logger.info("createPatient: Create a new patient from request: $patientRequest")

        val patient = patientRequest.toEntity()
        patientRepository.save(patient)
    }

    override fun findAll(): List<PatientResponse> {
        logger.info("findAll: Query all patients from the database")

        val result = patientRepository.findAll().map { it.toResponse(statusResponseForPatient(it.id)) }

        logger.info("findAll: Result: $result")
        return result
    }

    override fun findPatient(id: Long): PatientResponse {
        logger.info("findPatient: find patient by id - $id")

        val patient = patientRepository.findByIdOrNull(id) ?: throw PatientNotFoundException(id)
        val patientStatus = statusResponseForPatient(id)
        val result = patient.toResponse(patientStatus)

        logger.info("findPatient: result is {}", result)
        return result
    }

    private fun statusResponseForPatient(id: Long) = runCatching { statusService.findCurrentStatus(id) }.getOrNull()
}