package com.itmo.mpa.service

import com.itmo.mpa.dto.request.DoctorRequest
import com.itmo.mpa.dto.response.DoctorResponse

interface DoctorService {

    fun createDoctor(request: DoctorRequest)

    fun findAll(): List<DoctorResponse>

    fun findById(id: Long): DoctorResponse

    fun findByName(name: String): List<DoctorResponse>
}