package com.itmo.mpa.service

import com.itmo.mpa.dto.request.DiseaseRequest
import com.itmo.mpa.dto.response.DiseaseResponse
import com.itmo.mpa.dto.response.MedicineResponse

interface DiseaseService {

    fun getAll(): List<DiseaseResponse>

    fun getMedicineByDiseaseId(id: Long): List<MedicineResponse>

    fun createDisease(diseaseRequest: DiseaseRequest)
}