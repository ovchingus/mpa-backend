package com.itmo.mpa.service

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.service.exception.NotFoundException


interface PatientService {

    fun createPatient(patientRequest: PatientRequest)

    fun findAll(): List<PatientResponse>

    /**
     *  Returns patient by given [id]
     *
     *  @param id patient id
     *  @return found patient
     *  @throws NotFoundException if patient not found
     */
    fun findPatient(id: Long): PatientResponse
}