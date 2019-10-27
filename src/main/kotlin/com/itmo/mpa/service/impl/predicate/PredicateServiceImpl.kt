package com.itmo.mpa.service.impl.predicate

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.impl.predicate.parser.Parser
import com.itmo.mpa.service.impl.predicate.parser.asString
import com.itmo.mpa.service.impl.predicate.parser.collectReferences
import com.itmo.mpa.service.impl.predicate.parser.evaluate
import com.itmo.mpa.service.impl.predicate.resolver.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PredicateServiceImpl(
    private val parser: Parser,
    private val symbolicNameResolverFacade: SymbolicNameResolverFacade
) : PredicateService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun testPredicate(
        patient: Patient?,
        draft: Status?,
        predicate: String
    ): Either<PredicateEvaluatingError, Boolean> {
        logger.info("testPredicate: parses predicate {}", predicate)
        val parsedExpression = parser.parse(predicate)
        logger.debug("testPredicate: predicate {} parsed to {}", predicate, parsedExpression.asString())
        val resolverParameters = ResolvingParameters(patient, draft)

        return resolveReferences(parsedExpression.collectReferences(), resolverParameters)
                .map { resolvedValues ->
                    parsedExpression.evaluate { referenceName ->
                        PredicateValue(resolvedValues.getValue(referenceName))
                    }
                }
    }

    override fun checkPredicate(predicate: String) {
        val parsed = parser.parse(predicate)
        if (logger.isDebugEnabled) {
            logger.debug("check predicate parsed {} to {}", predicate, parsed.asString())
        }
    }

    private fun resolveReferences(
        referenceNames: Set<String>,
        resolverParameters: ResolvingParameters
    ): Either<PredicateEvaluatingError, Map<String, String>> {

        val (errors, resolved) = referenceNames
                .map { it.resolveReference(resolverParameters) }
                .partition { it.isLeft() }

        if (errors.isNotEmpty()) {
            return errors.fold(emptyList()) { acc: List<PredicateError>, next ->
                next.fold({ acc + it }, { acc })
            }.let { PredicateEvaluatingError(it).left() }
        }

        return resolved.fold(emptyMap()) { acc: Map<String, String>, next ->
            next.fold({ acc }, { acc + it })
        }.right()
    }

    private fun String.resolveReference(
        resolverParameters: ResolvingParameters
    ): Either<PredicateError, Pair<String, String>> {
        return symbolicNameResolverFacade.resolve(resolverParameters, this)
                .mapLeft { err ->
                    when (err) {
                        is UnresolvedPropertyError -> PredicateError(err.code, err.reason)
                        is NoMatchedResolverError -> PredicateError(err.code, err.reference)
                        IllegalResolvingStateError -> throw IllegalStateException(err.toString())
                    }
                }
                .map { this to it }
    }
}
