package com.itmo.mpa.service.impl.parsing

class UnexpectedTokenException(token: String) : RuntimeException("Unexpected token: $token")