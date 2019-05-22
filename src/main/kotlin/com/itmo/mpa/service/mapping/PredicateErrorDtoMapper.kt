package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.PredicateErrorResponse
import com.itmo.mpa.service.impl.predicate.PredicateError

fun PredicateError.toResponse() = PredicateErrorResponse(code, reason)