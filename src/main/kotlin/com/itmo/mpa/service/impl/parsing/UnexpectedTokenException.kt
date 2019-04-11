package com.itmo.mpa.service.impl.parsing

class UnexpectedTokenException(val token: String) : RuntimeException("Unexpected token: $token")