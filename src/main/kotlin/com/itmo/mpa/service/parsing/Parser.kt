package com.itmo.mpa.service.parsing

import org.springframework.stereotype.Component

@Component
class Parser {

    fun parse(expression: String) : Boolean {
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
                      openBracketsOnPrefix : List<Int>,
                      closeBracketsOnSuffix : List<Int>) : Boolean {
        //todo not implemented
        return true
    }

    private fun partition(expression: String, leading: Int, following: Int) : Int {
        //todo not implemented
        return 0
    }
}