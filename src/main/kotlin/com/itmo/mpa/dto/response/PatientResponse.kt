package com.itmo.mpa.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class PatientResponse(
        val id: Long,
        val name: String,
        @JsonFormat(pattern = "yyyy-MM-dd")
        val birthDate: LocalDate,
        val status: StatusResponse?,
        val diseaseName: String?,
        val doctor: DoctorResponse
)