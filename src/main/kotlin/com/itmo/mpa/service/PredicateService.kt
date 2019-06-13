package com.itmo.mpa.service

import arrow.core.Either
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.service.impl.predicate.PredicateEvaluatingError
import com.itmo.mpa.service.impl.predicate.parser.exception.UnexpectedTokenException

interface PredicateService {

    /**
     * Parses given [predicate] and test it using [patient] and [draft] as parameters
     *
     * @param patient patient to test predicate with
     * @param draft current status' draft of patient
     * @param predicate predicate expression to check
     * @return either an evaluating error or test's result
     * @throws [UnexpectedTokenException] if predicate is malformed
     */
    fun testPredicate(patient: Patient?, draft: Status?, predicate: String): Either<PredicateEvaluatingError, Boolean>

    /**
     *  Parses given [predicate] to check if it is malformed.
     *
     *  @throws UnexpectedTokenException if predicate is malformed
     */
    fun checkPredicate(predicate: String)
}
