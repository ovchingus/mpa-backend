package com.itmo.mpa.service.parsing

import com.itmo.mpa.service.parsing.model.*

import org.springframework.stereotype.Component

@Component
class Parser {

    private val supportedOperations = Operations.values()
            .groupBy { it.token }
            .mapValues { it.value.first() }

    fun parse(expression: String): BinaryExpression<Either<Double, String>> {
        val trimmedExpression = expression.filterNot { it -> it == ' ' }
        return parseExpression(trimmedExpression, 0, trimmedExpression.length)
    }

    private fun parseExpression(expression: String,
                                leading: Int,
                                following: Int): BinaryExpression<Either<Double, String>> {

        val resultExpression: BinaryExpression<Either<Double, String>>?
        var head = leading
        var expressionCandidate = ""
        while (true) {
            expressionCandidate += expression[head]

            if (supportedOperations.contains(expressionCandidate)) {
                val operation = supportedOperations[expressionCandidate]!!

                resultExpression = when (operation) {
                    Operations.NOT -> Not(parseExpression(expression, head + 2, following - 1))
                    Operations.AND, Operations.OR -> {
                        val delimiter = partition(expression, head + 2, following)
                        val left = parseExpression(expression, head + 2, delimiter + 1)
                        val right = parseExpression(expression, delimiter + 2, following - 1)
                        when (operation) {
                            Operations.AND -> And(left, right)
                            Operations.OR -> Or(left, right)
                            else -> null
                        }
                    }
                    Operations.EQ, Operations.GT, Operations.LT -> {
                        val delimiter = expression.indexOf(',', head + 2, false)
                        val left = parseValue(expression, head + 2, delimiter)
                        val right = parseValue(expression, delimiter + 1, following - 1)
                        when (operation) {
                            Operations.EQ -> Equal(left, right)
                            Operations.GT -> GreaterThan(left, right)
                            Operations.LT -> LessThan(left, right)
                            else -> null
                        }
                    }
                }
                return resultExpression!!
            } else {
                head++
            }
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
