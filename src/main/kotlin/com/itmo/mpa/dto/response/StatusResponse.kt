package com.itmo.mpa.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.itmo.mpa.util.InstantSerializer
import java.time.Instant

data class StatusResponse(
    val id: Long,
    @JsonSerialize(using = InstantSerializer::class)
    val submittedOn: Instant,
    val state: StateResponse,
    val attributes: List<DiseaseAttributeValueResponse>,
    val medicines: List<MedicineResponse>
)
