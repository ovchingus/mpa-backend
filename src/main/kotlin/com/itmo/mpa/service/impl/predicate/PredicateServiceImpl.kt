package com.itmo.mpa.service.impl.predicate

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.impl.predicate.parser.Parser
import com.itmo.mpa.service.impl.predicate.parser.asString
import com.itmo.mpa.service.impl.predicate.parser.collectReferences
import com.itmo.mpa.service.impl.predicate.parser.evaluate
import com.itmo.mpa.service.impl.predicate.resolver.ResolvingException
import com.itmo.mpa.service.impl.predicate.resolver.ResolvingParameters
import com.itmo.mpa.service.impl.predicate.resolver.SymbolicNameResolverFacade
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PredicateServiceImpl(
        private val parser: Parser,
        private val symbolicNameResolverFacade: SymbolicNameResolverFacade
) : PredicateService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun testPredicate(patient: Patient?, draft: Status?, predicate: String): Boolean {
        logger.info("testPredicate: parses predicate {}", predicate)
        val parsedExpression = parser.parse(predicate)
        logger.debug("testPredicate: predicate {} parsed to {}", predicate, parsedExpression.asString())
        val resolverParameters = ResolvingParameters(patient, draft)

        val resolvedValues = resolveReferences(parsedExpression.collectReferences(), resolverParameters)
        return parsedExpression.evaluate { referenceName ->
            PredicateValue(resolvedValues.getValue(referenceName))
        }
    }

    private fun resolveReferences(
            parsedExpression: Set<String>,
            resolverParameters: ResolvingParameters
    ): Map<String, String> {
        val resolvingErrors = mutableListOf<Throwable>()
        val resolvedValues = parsedExpression.mapNotNull { referenceName ->
            runCatching { referenceName to symbolicNameResolverFacade.resolve(resolverParameters, referenceName) }
                    .onFailure { resolvingErrors += it }
                    .getOrNull()
        }

        if (resolvingErrors.isEmpty()) return resolvedValues.toMap()

        val errors = resolvingErrors.map { error ->
            when (error) {
                is ResolvingException -> PredicateError(error.code, error.reason)
                is NumberFormatException -> PredicateError(PredicateErrorCode.NUMBER_FORMAT.code, error.message!!)
                else -> PredicateError(PredicateErrorCode.UNKNOWN_ERROR.code, error.toString())
            }
        }

        throw PredicateException(errors)
    }

    override fun checkPredicate(predicate: String) {
        val parsed = parser.parse(predicate)
        if (logger.isDebugEnabled) {
            logger.debug("check predicate parsed {} to {}", predicate, parsed.asString())
        }
    }
}
