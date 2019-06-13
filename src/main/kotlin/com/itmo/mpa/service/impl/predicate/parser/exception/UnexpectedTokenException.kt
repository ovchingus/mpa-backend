package com.itmo.mpa.service.impl.predicate.parser.exception

class UnexpectedTokenException(token: String) : RuntimeException("Unexpected token: $token")
