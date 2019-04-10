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

            if (supportedOperations.contains(expressionCandidate)) {
                val operation = supportedOperations[expressionCandidate]!!

                return when (operation.type) {
                    UnaryOperation -> parseUnary(operation, expression, head, following)
                    LogicalOperation -> parseLogical(operation, expression, head, following)
                    ComparisonOperation -> parseComparison(operation, expression, head, following)
                }
            } else {
                head++
            }
        }
    }

    private fun parseComparison(
            operation: Operation,
            expression: String,
            head: Int,
            following: Int
    ): BinaryExpression<Either<Double, String>> {
        val delimiter = expression.indexOf(',', head + 2, false)
        val left = parseValue(expression, head + 2, delimiter)
        val right = parseValue(expression, delimiter + 1, following - 1)

        return when (operation) {
            Operation.EQ -> Equal(left, right)
            Operation.GT -> GreaterThan(left, right)
            Operation.LT -> LessThan(left, right)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun parseLogical(
            operation: Operation,
            expression: String,
            head: Int,
            following: Int
    ): BinaryExpression<Either<Double, String>> {
        val delimiter = partition(expression, head + 2, following)
        val left = parseExpression(expression, head + 2, delimiter + 1)
        val right = parseExpression(expression, delimiter + 2, following - 1)

        return when (operation) {
            Operation.AND -> And(left, right)
            Operation.OR -> Or(left, right)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun parseUnary(
            operation: Operation,
            expression: String,
            head: Int,
            following: Int
    ): Not<Either<Double, String>> {
        val argument = parseExpression(expression, head + 2, following - 1)

        return when (operation) {
            Operation.NOT -> Not(argument)
            else -> throw UnexpectedTokenException(operation.token)
        }
    }

    private fun partition(expression: String, leading: Int, following: Int): Int {

        var balance = 0
        var result = leading
        for (i in leading..following) {
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

    private fun parseValue(expression: String,
                           leading: Int,
                           following: Int): Value<Either<Double, String>> {
        if (expression[leading] == '$') {
            return UnknownValue(expression.subSequence(leading + 1, following).toString().toInt())
        } else {
            return try {
                KnownValue(Numerical(expression.subSequence(leading, following).toString().toDouble()))
            } catch (ex: NumberFormatException) {
                KnownValue(Textual(expression.subSequence(leading, following).toString()))
            }
        }
    }
}
