package com.itmo.mpa.service.parsing

import org.springframework.stereotype.Component

@Component
class Parser {

    fun parse(expression: String) : Boolean {
        //todo not implemented
        return true
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