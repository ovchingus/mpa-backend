package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.NotFoundException
import com.itmo.mpa.service.PatientService
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PatientServiceImpl(
        private val patientRepository: PatientRepository,
        private val statusRepository: StatusRepository
) : PatientService {

    override fun createPatient(patientRequest: PatientRequest) {
        val patient = if (patientRequest.statusId != null) {
            patientRequest.toEntity(statusEntity = statusRepository.findByIdOrNull(patientRequest.statusId))
        } else {
            patientRequest.toEntity()
        }
        patientRepository.save(patient)
    }

    override fun findAll(): List<PatientResponse> = patientRepository.findAll().map { it.toResponse() }

    override fun findPatient(id: Long): PatientResponse? = patientRepository.findByIdOrNull(id)?.toResponse()

    override fun changeStatus(patientId: Long, statusId: Long) {
        val patient = patientRepository.findByIdOrNull(patientId)
                ?: throw  NotFoundException("Patient $patientId not found")
        val status = statusRepository.findByIdOrNull(statusId)
                ?: throw NotFoundException("Status $statusId not found")
        patient.status = status
        patientRepository.save(patient)
    }
}