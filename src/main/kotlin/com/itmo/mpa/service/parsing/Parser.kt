package com.itmo.mpa.service.parsing

import com.itmo.mpa.service.parsing.model.*

import org.springframework.stereotype.Component

@Component
class Parser {

    private val supportedOperations = Operation.values()
            .groupBy { it.token }
            .mapValues { it.value.first() }

    fun parse(expression: String): BinaryExpression<Either<Double, String>> {
        val trimmedExpression = expression.filterNot { it -> it == ' ' }
        return parseExpression(trimmedExpression)
    }

    private fun parseExpression(
            expression: String,
            leading: Int = 0,
            following: Int = expression.length
    ): BinaryExpression<Either<Double, String>> {
        var head = leading
        var expressionCandidate = ""

        while (true) {
            expressionCandidate += expression[head]

            if (!supportedOperations.contains(expressionCandidate)) {
                head++
                if (head == expression.length) throw UnexpectedTokenException(expression)
                continue
            }

            val operation = supportedOperations[expressionCandidate]!!
            val arguments = ParsingArguments(operation, expression, head, following)
            return when (operation.type) {
                UnaryOperation -> parseUnary(arguments)
                LogicalOperation -> parseLogical(arguments)
                ComparisonOperation -> parseComparison(arguments)
            }
        }
    }

    private fun parseComparison(args: ParsingArguments): BinaryExpression<Either<Double, String>> {
        val (operation, expression, head, following) = args

        val delimiter = expression.indexOf(',', head + 2, false)
        if (delimiter < 0) throw UnexpectedTokenException(expression)

        val left = parseValue(expression, head + 2, delimiter)
        val right = parseValue(expression, delimiter + 1, following - 1)

        return when (operation) {
            Operation.EQ -> Equal(left, right)
            Operation.GT -> GreaterThan(left, right)
            Operation.LT -> LessThan(left, right)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun parseLogical(args: ParsingArguments): BinaryExpression<Either<Double, String>> {
        val (operation, expression, head, following) = args

        val delimiter = partitionIndex(expression, head + 2, following)
        if (delimiter > following) throw UnexpectedTokenException(expression)

        val left = parseExpression(expression, head + 2, delimiter + 1)
        val right = parseExpression(expression, delimiter + 2, following - 1)

        return when (operation) {
            Operation.AND -> And(left, right)
            Operation.OR -> Or(left, right)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun parseUnary(args: ParsingArguments): BinaryExpression<Either<Double, String>> {
        val (operation, expression, head, following) = args

        val argument = parseExpression(expression, head + 2, following - 1)
        return when (operation) {
            Operation.NOT -> Not(argument)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun partitionIndex(expression: String, leading: Int, following: Int): Int {

        var balance = 0
        var result = leading
        for (i in leading until following) {
            if (expression[i] == '(') {
                balance++
            } else if (expression[i] == ')') {
                balance--
            } else if (expression[i] == ',' && balance == 0) {
                result = i - 1
                break
            }
        }
        return result
    }

    private fun parseValue(
            expression: String,
            leading: Int,
            following: Int
    ): Value<Either<Double, String>> {
        if (expression[leading] == '$') {
            return UnknownValue(expression.subSequence(leading + 1, following).toString().toInt())
        }
        return try {
            KnownValue(Numerical(expression.subSequence(leading, following).toString().toDouble()))
        } catch (ex: NumberFormatException) {
            KnownValue(Textual(expression.subSequence(leading, following).toString()))
        }
    }
}
