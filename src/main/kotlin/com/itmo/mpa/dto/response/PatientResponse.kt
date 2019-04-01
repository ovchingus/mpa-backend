package com.itmo.mpa.dto.response

import com.itmo.mpa.dto.StatusDto

data class PatientResponse(val id: Long, val name: String, val status: StatusDto?)