package com.itmo.mpa.service

import com.itmo.mpa.dto.request.DiseaseRequest
import com.itmo.mpa.dto.response.DiseaseResponse

interface DiseaseService {

    fun getAll(): List<DiseaseResponse>

    fun createDisease(diseaseRequest: DiseaseRequest)
}