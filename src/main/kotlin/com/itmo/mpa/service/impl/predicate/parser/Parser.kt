package com.itmo.mpa.service.impl.predicate.parser

import com.itmo.mpa.service.impl.predicate.PredicateValue
import com.itmo.mpa.service.impl.predicate.parser.exception.UnexpectedTokenException
import org.springframework.stereotype.Component

@Component
class Parser {

    companion object {

        private const val ARGS_DELIMITER = ','
        private const val REFERENCE_PREFIX = '$'
    }

    private val supportedOperations = Operation.values()
            .groupBy { it.token }
            .mapValues { it.value.first() }

    fun parse(expression: String): BinaryExpression<PredicateValue> {
        val trimmedExpression = expression.filterNot { it == ' ' }
        return parseExpression(trimmedExpression)
    }

    /**
     *   expression
     *   |        |
     *   begin    end
     */
    private fun parseExpression(
            expression: String,
            begin: Int = 0,
            end: Int = expression.length - 1
    ): BinaryExpression<PredicateValue> {
        var head = begin
        var expressionCandidate = ""

        while (expression[head].isLetter()) {
            expressionCandidate += expression[head]
            head++
        }

        val operation = supportedOperations[expressionCandidate]
                ?: throw UnexpectedTokenException(expressionCandidate)

        val arguments = ParsingArguments(operation, expression, head + 1, end - 1)
        return when (operation.type) {
            UnaryOperation -> parseUnary(arguments)
            LogicalOperation -> parseLogical(arguments)
            ComparisonOperation -> parseComparison(arguments)
        }
    }

    private fun parseComparison(args: ParsingArguments): BinaryExpression<PredicateValue> {
        val (operation, expression, begin, end) = args

        val delimiter = partitionIndex(expression, begin, end)

        val left = parseValue(expression, begin, delimiter - 1)
        val right = parseValue(expression, delimiter + 1, end)

        return when (operation) {
            Operation.EQ -> Equal(left, right)
            Operation.GT -> GreaterThan(left, right)
            Operation.GTE -> GreaterThanEqual(left, right)
            Operation.LT -> LessThan(left, right)
            Operation.LTE -> LessThanEqual(left, right)
            Operation.HAS -> Has(left, right)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun parseLogical(args: ParsingArguments): BinaryExpression<PredicateValue> {
        val (operation, expression, begin, end) = args

        val delimiter = partitionIndex(expression, begin, end)

        val left = parseExpression(expression, begin, delimiter - 1)
        val right = parseExpression(expression, delimiter + 1, end)

        return when (operation) {
            Operation.AND -> And(left, right)
            Operation.OR -> Or(left, right)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun parseUnary(args: ParsingArguments): BinaryExpression<PredicateValue> {
        val (operation, expression, begin, end) = args

        val argument = parseExpression(expression, begin, end)
        return when (operation) {
            Operation.NOT -> Not(argument)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun partitionIndex(expression: String, begin: Int, end: Int): Int {
        var balance = 0
        for (i in begin until end) {
            if (expression[i] == '(') {
                balance++
            } else if (expression[i] == ')') {
                balance--
            } else if (expression[i] == ARGS_DELIMITER && balance == 0) {
                return i
            }
        }
        throw UnexpectedTokenException(expression)
    }

    private fun parseValue(
            expression: String,
            begin: Int,
            end: Int
    ): Value<PredicateValue> {
        val inclusiveEnd = end + 1
        if (expression[begin] == REFERENCE_PREFIX) {
            return UnknownValue(expression.substring(begin + 1, inclusiveEnd))
        }
        return KnownValue(PredicateValue(expression.substring(begin, inclusiveEnd)))
    }
}
