package com.itmo.mpa.service.impl.parsing

import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.impl.parsing.model.Either
import com.itmo.mpa.service.impl.parsing.model.evaluate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PredicateServiceImpl(
        private val parser: Parser
) : PredicateService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun parsePredicate(predicate: String): (List<Either<Double, String>>) -> Boolean {
        logInfo("parsePredicate: parses predicate - $predicate")
        val parsedExpression = parser.parse(predicate)
        return { parsedExpression.evaluate(it) }
    }

    private fun logInfo(msg: String) {
            logger.info("PREDICATE_SERVICE: $msg")
    }
}
