package com.itmo.mpa.dto.response

data class PatientResponse(val id: Int, val name: String, val age: Int, val status: StatusResponse)