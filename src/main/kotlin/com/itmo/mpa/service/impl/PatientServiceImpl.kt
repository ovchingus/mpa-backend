package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.DiseaseRepository
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.PatientService
import com.itmo.mpa.service.exception.DiseaseNotFoundException
import com.itmo.mpa.service.impl.entityservice.DoctorEntityService
import com.itmo.mpa.service.impl.entityservice.PatientStatusEntityService
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
        private val patientRepository: PatientRepository,
        private val diseaseRepository: DiseaseRepository,
        private val doctorEntityService: DoctorEntityService,
        private val patientStatusEntityService: PatientStatusEntityService
) : PatientService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun createPatient(patientRequest: PatientRequest): PatientResponse {
        logger.info("createPatient: Create a new patient from request {}", patientRequest)

        val doctor = doctorEntityService.findById(patientRequest.doctorId!!)

        val disease = diseaseRepository.findByIdOrNull(patientRequest.diseaseId!!)
                ?: throw DiseaseNotFoundException(patientRequest.diseaseId)

        val patient = patientRequest.toEntity(disease, doctor)
        return patientRepository.save(patient).toResponse()
    }

    override fun findAll(): List<PatientResponse> {
        logger.info("findAll: Query all patients from the database")
        return patientRepository.findAll().map { it.toResponse() }
                .also { logger.debug("findAll returned {}", it) }
    }

    override fun findPatient(id: Long): PatientResponse {
        logger.info("findPatient: find patient by id {}", id)
        val patient = patientStatusEntityService.findPatient(id)
        return patient.toResponse()
                .also { logger.info("findPatient {} returned {}", id, it) }
    }
}