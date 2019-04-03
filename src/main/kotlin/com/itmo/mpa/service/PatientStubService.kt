package com.itmo.mpa.service

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.mapping.toResponse
import com.itmo.mpa.service.mapping.toEntity
import org.springframework.stereotype.Service

@Service
class PatientStubService(private val patientRepository: PatientRepository, private val statusRepository: StatusRepository) : PatientService {


    override fun createPatient(patientRequest: PatientRequest) {

        val status = patientRequest.statusId?.let { statusRepository.findById(patientRequest.statusId).orElse(null) }
        patientRepository.save(patientRequest.toEntity(status))
    }

    override fun findAll(): List<PatientResponse> = patientRepository.findAll().map { it.toResponse() }

    override fun findPatient(id: Long): PatientResponse? = patientRepository.findById(id)
            .map { it.toResponse() }
            .orElse(null)

    override fun changeStatus(patientId: Long, statusId: Long) {
        val patient = patientRepository.findById(patientId).orElseThrow { NotFoundException("Patient $patientId not found") }
        val status = statusRepository.findById(statusId).orElseThrow { NotFoundException("Status $statusId not found") }
        patient.status = status
        patientRepository.save(patient)
    }

}