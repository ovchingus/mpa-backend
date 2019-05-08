package com.itmo.mpa.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.itmo.mpa.util.InstantSerializer
import java.time.Instant

data class PatientResponse(
        val id: Long,
        val name: String,
        @JsonSerialize(using = InstantSerializer::class)
        val birthDate: Instant,
        val status: StatusResponse?,
        val diseaseName: String?,
        val doctor: DoctorResponse
)