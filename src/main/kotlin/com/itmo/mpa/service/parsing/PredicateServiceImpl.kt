package com.itmo.mpa.service.parsing

import com.itmo.mpa.service.parsing.model.Either
import com.itmo.mpa.service.parsing.model.evaluate
import org.springframework.stereotype.Service

@Service
class PredicateServiceImpl(
        private val parser: Parser
) : PredicateService {

    override fun parsePredicate(predicate: String): (List<Either<Double, String>>) -> Boolean {
        val parsedExpression = parser.parse(predicate)
        return { parsedExpression.evaluate(it) }
    }
}
