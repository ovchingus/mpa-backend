package com.itmo.mpa.service.parsing

import com.itmo.mpa.service.parsing.model.Either
import com.sun.javafx.fxml.expression.BinaryExpression
import org.springframework.stereotype.Component

@Component
class Parser {

    private val supportedOperations = Operations.values().map { it.token }

    fun parse(expression: String): (List<Either<Int, String>>) -> Boolean {
        val openBracketsOnPrefix = ArrayList<Int>()
        val closeBracketsOnSuffix = ArrayList<Int>()

        var openBrackets = 0
        var closeBrackets = 0

        for (character in expression) {
            if (character == '(') {
                openBracketsOnPrefix.add(++openBrackets)
            } else {
                openBracketsOnPrefix.add(openBrackets)
            }
        }

        for (character in expression) {
            if (character == ')') {
                closeBracketsOnSuffix.add(++closeBrackets)
            } else {
                closeBracketsOnSuffix.add(openBrackets - closeBrackets++)
            }
        }

        return parse(expression, 0, expression.length, openBracketsOnPrefix, closeBracketsOnSuffix)
    }

    private fun parse(expression: String,
                      leading: Int,
                      following: Int,
                      openBracketsOnPrefix: List<Int>,
                      closeBracketsOnSuffix: List<Int>): (List<Either<Int, String>>) -> Boolean {
        var resultExpression: Nothing?
        var head = leading
        var exprCandidate = ""
        while (true) {
            exprCandidate += expression[head]
            if (supportedOperations.contains(exprCandidate)) {
                resultExpression = when (exprCandidate) {
                    Operations.NOT.token -> not(parse(expression, head + 2, following - 1, openBracketsOnPrefix, closeBracketsOnSuffix))
                    Operations.AND.token, Operations.EQ.token, Operations.GT.token, Operations.LT.token, Operations.OR.token -> {
                        val delimiter = partition(head + 2, following - 1, openBracketsOnPrefix, closeBracketsOnSuffix)
                        when (exprCandidate) {
                            Operations.AND.token -> and(parse(expression, head + 2, delimiter, openBracketsOnPrefix, closeBracketsOnSuffix),
                                    parse(expression, delimiter + 1, following, openBracketsOnPrefix, closeBracketsOnSuffix))
                            Operations.OR.token -> or(parse(expression, head + 2, delimiter, openBracketsOnPrefix, closeBracketsOnSuffix),
                                    parse(expression, delimiter + 1, following, openBracketsOnPrefix, closeBracketsOnSuffix))
                            Operations.EQ.token -> eq(parse(expression, head + 2, delimiter, openBracketsOnPrefix, closeBracketsOnSuffix),
                                    parse(expression, delimiter + 1, following, openBracketsOnPrefix, closeBracketsOnSuffix))
                            Operations.GT.token -> gt(parse(expression, head + 2, delimiter, openBracketsOnPrefix, closeBracketsOnSuffix),
                                    parse(expression, delimiter + 1, following, openBracketsOnPrefix, closeBracketsOnSuffix))
                            Operations.LT.token -> lt(parse(expression, head + 2, delimiter, openBracketsOnPrefix, closeBracketsOnSuffix),
                                    parse(expression, delimiter + 1, following, openBracketsOnPrefix, closeBracketsOnSuffix))
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


    fun partition(leading: Int,
                  following: Int,
                  openBracketsOnPrefix: List<Int>,
                  closeBracketsOnSuffix: List<Int>): Int {
        val x = openBracketsOnPrefix[openBracketsOnPrefix.size - 1] - openBracketsOnPrefix[leading]
        var l = leading
        var r = following
        while (r - l > 1) {
            val middle = (l + r) / 2
            if (closeBracketsOnSuffix[middle] >= x) {
                l = middle
            } else {
                r = middle
            }
        }
        return l
    }
}
