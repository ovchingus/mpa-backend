package com.itmo.mpa.service.parsing

class UnexpectedTokenException(val token: String) : RuntimeException("Unexpected token: $token")