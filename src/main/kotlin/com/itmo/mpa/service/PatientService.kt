package com.itmo.mpa.service

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.PatientResponse

interface PatientService {

    fun createPatient(patientRequest: PatientRequest)

    fun findAll(): List<PatientResponse>

    fun findPatient(id: Long): PatientResponse?

    fun changeStatus(id: Long, statusRequest: StatusRequest)
}