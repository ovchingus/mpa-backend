package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
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
        val patient = patientRequest.toEntity()
        patientRepository.save(patient)
    }

    override fun findAll(): List<PatientResponse> = patientRepository.findAll().map { it.toResponse() }

    override fun findPatient(id: Long): PatientResponse? = patientRepository.findByIdOrNull(id)?.toResponse()
}