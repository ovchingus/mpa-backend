package com.itmo.mpa.service

import com.itmo.mpa.service.impl.parsing.model.Either

interface PredicateService {

    /**
     * Parses the given [predicate] to produce a lambda which can be called many times without re-parsing
     *
     * @return a closure over parsed predicate which takes a list of arguments to test against
     * @param predicate to parse
     */
    fun parsePredicate(predicate: String): (List<Either<Double, String>>) -> Boolean
}
