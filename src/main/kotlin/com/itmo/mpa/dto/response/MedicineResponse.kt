package com.itmo.mpa.dto.response

data class MedicineResponse(
        val id: Long,
        val name: String,
        val activeSubstances: List<String>
)
