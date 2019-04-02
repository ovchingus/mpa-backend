package com.itmo.mpa.service.parsing

import org.springframework.stereotype.Component

@Component
class Parser {

    private val supportedOperations = Operations.values().map { it.token }

    fun parse(expression: String): Boolean {
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
                      closeBracketsOnSuffix: List<Int>): Boolean {
        //todo not implemented
        return true
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
