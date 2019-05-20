package com.itmo.mpa.service.impl.parsing

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.impl.parsing.model.PredicateValue
import com.itmo.mpa.service.impl.parsing.model.asString
import com.itmo.mpa.service.impl.parsing.model.evaluate
import com.itmo.mpa.service.impl.resolver.ResolvingParameters
import com.itmo.mpa.service.impl.resolver.SymbolicNameResolverFacade
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
        return parsedExpression.evaluate { referenceName ->
            symbolicNameResolverFacade.resolve(resolverParameters, referenceName)?.let { PredicateValue(it) }
                    ?: throw NullReferenceException(referenceName)
        }
    }

    override fun checkPredicate(predicate: String) {
        val parsed = parser.parse(predicate)
        if (logger.isDebugEnabled) {
            logger.debug("check predicate parsed {} to {}", predicate, parsed.asString())
        }
    }
}
