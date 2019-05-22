package com.itmo.mpa.service

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.service.impl.predicate.PredicateException
import com.itmo.mpa.service.impl.predicate.parser.exception.UnexpectedTokenException

interface PredicateService {

    /**
     * Parses given [predicate] and test it using [patient] and [draft] as parameters
     *
     * @param patient patient to test predicate with
     * @param draft current status' draft of patient
     * @param predicate predicate expression to check
     * @return test's result
     * @throws PredicateException if at least one reference from predicate was resolved to null
     * @throws UnexpectedTokenException if predicate is malformed
     */
    fun testPredicate(patient: Patient?, draft: Status?, predicate: String): Boolean

    /**
     *  Parses given [predicate] to check if it is malformed.
     *
     *  @throws UnexpectedTokenException if predicate is malformed
     */
    fun checkPredicate(predicate: String)
}
