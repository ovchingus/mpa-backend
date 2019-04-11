package com.itmo.mpa.dto.response

data class StatusResponse(val id: Long, val submittedOn: Instant, val draft: Boolean)