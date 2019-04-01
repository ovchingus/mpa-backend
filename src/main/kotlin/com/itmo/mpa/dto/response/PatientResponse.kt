package com.itmo.mpa.dto.response

import com.itmo.mpa.dto.request.StatusRequest

data class PatientResponse(val id: Long, val name: String, val status: StatusRequest?)