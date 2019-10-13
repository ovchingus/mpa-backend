package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.MedicineResponse
import com.itmo.mpa.entity.medicine.Medicine

fun Medicine.toResponse() = MedicineResponse(
        id = this.id,
        name = this.name,
        activeSubstances = this.activeSubstance.map { it.name }
)
