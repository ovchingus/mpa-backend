package com.itmo.mpa.service

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.mapping.toDto
import com.itmo.mpa.service.mapping.toModel
import org.springframework.stereotype.Service

@Service
class PatientStubService(private val patientRepository: PatientRepository) : PatientService {


    override fun createPatient(patientRequest: PatientRequest) {
        println(patientRequest.toModel().age)
        patientRepository.save(patientRequest.toModel())
    }

    override fun findAll(): List<PatientResponse> = patientRepository.findAll().map { it.toDto() }

    override fun findPatient(id: Long): PatientResponse? {
        return patientRepository.findById(id)
                .map { it.toDto() }
                .orElse(null)
    }

    override fun changeStatus(id: Long, statusRequest: StatusRequest) {
        val patient = patientRepository.findById(id).orElseThrow { NotFoundException("Patient $id not found") }
        patientRepository.save(patient)
    }
}