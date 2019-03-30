package com.itmo.mpa.service

import com.itmo.mpa.controller.request.PatientRequest
import com.itmo.mpa.controller.request.StatusRequest
import com.itmo.mpa.controller.response.PatientResponse

interface PatientService {

    fun createPatient(patientRequest: PatientRequest)

    fun findAll(): List<PatientResponse>

    fun findPatient(id: Int): PatientResponse?

    fun changeStatus(id: Int, statusRequest: StatusRequest)
}