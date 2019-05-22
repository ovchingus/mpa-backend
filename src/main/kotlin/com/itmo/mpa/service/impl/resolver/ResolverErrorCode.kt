package com.itmo.mpa.service.impl.resolver

enum class ResolverErrorCode(val code: Int) {

    STATUS(101),
    PATIENT(102),
    MEDICINE(103),
    UNKNOWN(199),
}
