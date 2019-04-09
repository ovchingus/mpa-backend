package com.itmo.mpa.service

import com.itmo.mpa.service.parsing.Parser
import com.itmo.mpa.service.parsing.model.Numerical
import com.itmo.mpa.service.parsing.model.Textual
import com.itmo.mpa.service.parsing.model.evaluate
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ParserTest {

    private val parser = Parser()

    private val expression = "and(lt(4,$0),or(not(eq($1,test)),gt($2,$3)))"


    @Test
    fun checkPositiveParserResult() {
        assertTrue(parser.parse(expression).evaluate(
                listOf(Numerical(5.0), Textual("tet"), Numerical(1.0), Numerical(30.0))))
    }

    @Test
    fun checkNegativeParserResult() {
        assertFalse(parser.parse(expression).evaluate(
                listOf(Numerical(2.0), Textual("test"), Numerical(1.0), Numerical(30.0))))
    }
}