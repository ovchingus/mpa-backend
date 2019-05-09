package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.DiseaseRepository
import com.itmo.mpa.repository.DoctorRepository
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.PatientService
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.DiseaseNotFoundException
import com.itmo.mpa.service.exception.DoctorNotFoundException
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
        private val patientRepository: PatientRepository,
        private val statusService: StatusService,
        private val diseaseRepository: DiseaseRepository,
        private val doctorRepository: DoctorRepository
) : PatientService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun createPatient(patientRequest: PatientRequest): PatientResponse {
        logger.info("createPatient: Create a new patient from request: $patientRequest")

        val doctor = doctorRepository.findByIdOrNull(patientRequest.doctorId!!)
                ?: throw DoctorNotFoundException(patientRequest.doctorId)

        val disease = diseaseRepository.findByIdOrNull(patientRequest.diseaseId!!)
                ?: throw DiseaseNotFoundException(patientRequest.diseaseId)

        val patient = patientRequest.toEntity(disease, doctor)
        return patientRepository.save(patient).toResponse(null)
    }

    override fun findAll(): List<PatientResponse> {
        logger.info("findAll: Query all patients from the database")

        val result = patientRepository.findAll().map { it.toResponse(it.currentStatus?.toResponse()) }

        logger.info("findAll: Result: $result")
        return result
    }

    override fun findPatient(id: Long): PatientResponse {
        logger.info("findPatient: find patient by id - $id")

        val patient = patientRepository.findByIdOrNull(id) ?: throw PatientNotFoundException(id)
        val patientStatus = patient.currentStatus?.toResponse()
        val result = patient.toResponse(patientStatus)

        logger.info("findPatient: result is {}", result)
        return result
    }
}