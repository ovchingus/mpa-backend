package com.itmo.mpa.service

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.service.exception.PatientNotFoundException


interface PatientService {

    fun createPatient(patientRequest: PatientRequest): PatientResponse

    fun findAll(): List<PatientResponse>

    /**
     *  Returns patient by given [id]
     *
     *  @param id patient id
     *  @return found patient
     *  @throws PatientNotFoundException if patient not found
     */
    fun findPatient(id: Long): PatientResponse
}