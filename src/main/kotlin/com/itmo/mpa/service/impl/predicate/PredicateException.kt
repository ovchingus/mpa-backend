package com.itmo.mpa.service.impl.predicate

class PredicateException(val errors: Collection<PredicateError>) : RuntimeException()
