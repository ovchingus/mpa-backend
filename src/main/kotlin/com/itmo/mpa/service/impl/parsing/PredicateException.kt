package com.itmo.mpa.service.impl.parsing

class PredicateException(val errors: Collection<PredicateError>) : RuntimeException()
