package com.itmo.mpa.service.parsing

import com.itmo.mpa.service.parsing.model.*

import org.springframework.stereotype.Component

@Component
class Parser {

    private val supportedOperations = Operations.values().map { it.token }

    fun parse(expression: String): BinaryExpression<Either<Double, String>> {
        val trimmedExpression = expression.filterNot { it -> it == ' ' }
        return parseExpression(trimmedExpression, 0, trimmedExpression.length)
    }

    private fun parseExpression(expression: String,
                                leading: Int,
                                following: Int): BinaryExpression<Either<Double, String>> {

        val resultExpression: BinaryExpression<Either<Double, String>>?
        var head = leading
        var exprCandidate = ""
        while (true) {
            exprCandidate += expression[head]
            if (supportedOperations.contains(exprCandidate)) {
                resultExpression = when (exprCandidate) {
                    Operations.NOT.token -> Not(parseExpression(expression, head + 2, following - 1))
                    Operations.AND.token, Operations.OR.token -> {
                        val delimiter = partition(expression, head + 2, following)
                        when (exprCandidate) {
                            Operations.AND.token -> And(parseExpression(expression, head + 2, delimiter + 1),
                                    parseExpression(expression, delimiter + 2, following - 1))
                            Operations.OR.token -> Or(parseExpression(expression, head + 2, delimiter + 1),
                                    parseExpression(expression, delimiter + 2, following - 1))
                            else -> null
                        }
                    }
                    Operations.EQ.token, Operations.GT.token, Operations.LT.token -> {
                        val delimiter = expression.indexOf(',', head + 2, false)
                        when (exprCandidate) {
                            Operations.EQ.token -> Equal(parseValue(expression, head + 2, delimiter),
                                    parseValue(expression, delimiter + 1, following - 1))
                            Operations.GT.token -> GreaterThan(parseValue(expression, head + 2, delimiter),
                                    parseValue(expression, delimiter + 1, following - 1))
                            Operations.LT.token -> LessThan(parseValue(expression, head + 2, delimiter),
                                    parseValue(expression, delimiter + 1, following - 1))
                            else -> null
                        }
                    }
                    else -> null
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
