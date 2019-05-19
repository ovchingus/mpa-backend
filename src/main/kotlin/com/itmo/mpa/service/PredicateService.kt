package com.itmo.mpa.service

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.service.impl.parsing.NullReferenceException
import com.itmo.mpa.service.impl.parsing.UnexpectedTokenException

interface PredicateService {

    /**
     * Parses given [predicate] and test it using [patient] and [draft] as parameters
     *
     * @param patient patient to test predicate with
     * @param draft current status' draft of patient
     * @param predicate predicate expression to check
     * @return test's result
     * @throws [NullReferenceException] if a reference from predicate was resolved to null
     * @throws [UnexpectedTokenException] if predicate is malformed
     */
    fun testPredicate(patient: Patient?, draft: Status?, predicate: String): Boolean
}
