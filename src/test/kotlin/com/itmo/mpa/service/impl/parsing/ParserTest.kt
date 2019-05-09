package com.itmo.mpa.service.impl.parsing

import com.itmo.mpa.service.impl.parsing.model.ArgumentsCountMismatchException
import com.itmo.mpa.service.impl.parsing.model.PredicateValue
import com.itmo.mpa.service.impl.parsing.model.asString
import com.itmo.mpa.service.impl.parsing.model.evaluate
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ParserTest {

    lateinit var parser: Parser

    @BeforeEach
    fun setup() {
        parser = Parser()
    }

    @Test
    fun `when predicate is empty exception is thrown`() {
        assertThrows<Exception> { parser.parse("") }
    }

    @Test
    fun `when predicate is logical and malformed exception is thrown`() {
        assertThrows<UnexpectedTokenException> { parser.parse("and(and)") }
    }

    @Test
    fun `when predicate is comparison and malformed exception is thrown`() {
        assertThrows<UnexpectedTokenException> {
            println(parser.parse("eq(and)").asString())
        }
    }

    @Test
    fun `test spaces are removed from predicate`() {
        val shouldBeTrue = parser.parse("eq(sp r ingb oo t ,  s p ringboo t )")
                .evaluate(emptyList())
        assertThat(shouldBeTrue, `is`(true))
    }

    @Test
    fun `when arguments count is less than requested exception is thrown`() {
        assertThrows<ArgumentsCountMismatchException> {
            parser.parse("eq($0, $2)").evaluate(listOf(0.asValue(), "".asValue()))
        }
    }

    @Nested
    inner class TestSimpleExpression {

        @Nested
        inner class TestWithConstants {

            @Nested
            inner class TestEquals {

                @Test
                fun `test equals with equal numbers`() {
                    val shouldBeTrue = parser.parse("eq(1.3,1.3)")
                            .evaluate(emptyList())
                    assertThat(shouldBeTrue, `is`(true))
                }

                @Test
                fun `test equals with different numbers`() {
                    val shouldBeFalse = parser.parse("eq(25,14.4)")
                            .evaluate(emptyList())
                    assertThat(shouldBeFalse, `is`(false))
                }

                @Test
                fun `test equals with equal strings`() {
                    val shouldBeTrue = parser.parse("eq(spring,spring)")
                            .evaluate(emptyList())
                    assertThat(shouldBeTrue, `is`(true))
                }

                @Test
                fun `test equals with different strings`() {
                    val shouldBeFalse = parser.parse("eq(java,javascript)")
                            .evaluate(emptyList())
                    assertThat(shouldBeFalse, `is`(false))
                }
            }

            @Nested
            inner class TestLessThan {

                @Test
                fun `test lessThan with equal numbers`() {
                    val shouldBeFalse = parser.parse("lt(1.3,1.3)")
                            .evaluate(emptyList())
                    assertThat(shouldBeFalse, `is`(false))
                }

                @Test
                fun `test lessThan with first number greater than second`() {
                    val shouldBeFalse = parser.parse("lt(-200,-300)")
                            .evaluate(emptyList())
                    assertThat(shouldBeFalse, `is`(false))
                }

                @Test
                fun `test lessThan with first number less than second`() {
                    val shouldBeTrue = parser.parse("lt(11,12)")
                            .evaluate(emptyList())
                    assertThat(shouldBeTrue, `is`(true))
                }

                @Test
                fun `when lessThan is comparing strings exception is thrown`() {
                    assertThrows<NumberFormatException> {
                        parser.parse("lt(aaa,b)")
                                .evaluate(emptyList())
                    }
                }

                @Test
                fun `when lessThan is comparing different types exception is thrown`() {
                    assertThrows<NumberFormatException> {
                        parser.parse("lt(10,2ten)")
                                .evaluate(emptyList())
                    }
                }
            }

            @Nested
            inner class TestGreaterThan {

                @Test
                fun `test greaterThan with equal numbers`() {
                    val shouldBeFalse = parser.parse("gt(1.3,1.3)")
                            .evaluate(emptyList())
                    assertThat(shouldBeFalse, `is`(false))
                }

                @Test
                fun `test greaterThan with first number greater than second`() {
                    val shouldBeTrue = parser.parse("gt(-200,-300)")
                            .evaluate(emptyList())
                    assertThat(shouldBeTrue, `is`(true))
                }

                @Test
                fun `test greaterThan with first number less than second`() {
                    val shouldBeFalse = parser.parse("gt(11,12)")
                            .evaluate(emptyList())
                    assertThat(shouldBeFalse, `is`(false))
                }

                @Test
                fun `when greaterThan is comparing strings exception is thrown`() {
                    assertThrows<NumberFormatException> {
                        parser.parse("gt(aaa,b)")
                                .evaluate(emptyList())
                    }
                }

                @Test
                fun `when greaterThan is comparing different types exception is thrown`() {
                    assertThrows<NumberFormatException> {
                        parser.parse("gt(10,2ten)")
                                .evaluate(emptyList())
                    }
                }
            }

            @Nested
            inner class TestHas {

                @Test
                fun `test has with equal strings`() {
                    val shouldBeTrue = parser.parse("has(144,144)")
                            .evaluate(emptyList())
                    assertThat(shouldBeTrue, `is`(true))
                }

                @Test
                fun `test has with first string elements count greater than second`() {
                    val shouldBeTrue = parser.parse("has(a;b;c;d, a)")
                            .evaluate(emptyList())
                    assertThat(shouldBeTrue, `is`(true))
                }

                @Test
                fun `test has with first string elements count less than second`() {
                    val shouldBeFalse = parser.parse("has(14;19, 14;15;22)")
                            .evaluate(emptyList())
                    assertThat(shouldBeFalse, `is`(false))
                }

                @Test
                fun `test has with first string elements count equal with second`() {
                    val shouldBeTrue = parser.parse("has(foo;19;bar, bar;foo)")
                            .evaluate(emptyList())
                    assertThat(shouldBeTrue, `is`(true))
                }
            }
        }

        @Nested
        inner class TestWithVariables {

            @Nested
            inner class OneVariable {

                @Nested
                inner class VariableFirst {

                    @Nested
                    inner class TestEquals {

                        @Test
                        fun `test equals with equal numbers`() {
                            val shouldBeTrue = parser.parse("eq($0, 42)")
                                    .evaluate(listOf(42.asValue()))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `test equals with different numbers`() {
                            val shouldBeFalse = parser.parse("eq($0, 45)")
                                    .evaluate(listOf(42.asValue()))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test equals with equal strings`() {
                            val shouldBeTrue = parser.parse("eq($0,spring)")
                                    .evaluate(listOf("spring".asValue()))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `test equals with different strings`() {
                            val shouldBeFalse = parser.parse("eq($0,javascript)")
                                    .evaluate(listOf("amazing".asValue()))
                            assertThat(shouldBeFalse, `is`(false))
                        }
                    }

                    @Nested
                    inner class TestLessThan {

                        @Test
                        fun `test lessThan with equal numbers`() {
                            val shouldBeFalse = parser.parse("lt($0, 100)")
                                    .evaluate(listOf(100.asValue()))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test lessThan with first number greater than second`() {
                            val shouldBeFalse = parser.parse("lt($0, -300)")
                                    .evaluate(listOf((-200).asValue()))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test lessThan with first number less than second`() {
                            val shouldBeTrue = parser.parse("lt($0, 12)")
                                    .evaluate(listOf(11.asValue()))
                            assertThat(shouldBeTrue, `is`(true))
                        }
                    }

                    @Nested
                    inner class TestGreaterThan {

                        @Test
                        fun `test greaterThan with equal numbers`() {
                            val shouldBeFalse = parser.parse("gt($0,1.3)")
                                    .evaluate(listOf(1.3.asValue()))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test greaterThan with first number greater than second`() {
                            val shouldBeTrue = parser.parse("gt($0,-300)")
                                    .evaluate(listOf((-200).asValue()))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `test greaterThan with first number less than second`() {
                            val shouldBeFalse = parser.parse("gt($0, 12)")
                                    .evaluate(listOf(0.5.asValue()))
                            assertThat(shouldBeFalse, `is`(false))
                        }
                    }
                }

                @Nested
                inner class VariableSecond {

                    @Nested
                    inner class TestEquals {

                        @Test
                        fun `test equals with equal numbers`() {
                            val shouldBeTrue = parser.parse("eq(42, $0)")
                                    .evaluate(listOf(42.asValue()))
                            assertThat(shouldBeTrue, `is`(true))
                        }
                    }

                    @Nested
                    inner class TestLessThan {

                        @Test
                        fun `test lessThan with first number greater than second`() {
                            val shouldBeFalse = parser.parse("lt(10,$0)")
                                    .evaluate(listOf(5.asValue()))
                            assertThat(shouldBeFalse, `is`(false))
                        }
                    }

                    @Nested
                    inner class TestGreaterThan {

                        @Test
                        fun `test greaterThan with first number greater than second`() {
                            val shouldBeTrue = parser.parse("gt(10,$0)")
                                    .evaluate(listOf(5.asValue()))
                            assertThat(shouldBeTrue, `is`(true))
                        }
                    }
                }


            }

            @Nested
            inner class BothAreVariables {
                @Nested
                inner class TestEquals {

                    @Test
                    fun `test equals with equal numbers`() {
                        val shouldBeTrue = parser.parse("eq($0, $0)")
                                .evaluate(listOf(42.asValue()))
                        assertThat(shouldBeTrue, `is`(true))
                    }

                    @Test
                    fun `test equals with equal numbers with different index`() {
                        val shouldBeTrue = parser.parse("eq($1, $0)")
                                .evaluate(listOf(42.asValue(), 42.asValue()))
                        assertThat(shouldBeTrue, `is`(true))
                    }

                    @Test
                    fun `test equals with different numbers with different index`() {
                        val shouldBeFalse = parser.parse("eq($1, $0)")
                                .evaluate(listOf(142.asValue(), 42.asValue()))
                        assertThat(shouldBeFalse, `is`(false))
                    }

                    @Test
                    fun `test equals with equal strings`() {
                        val shouldBeTrue = parser.parse("eq($0, $0)")
                                .evaluate(listOf("spring".asValue(), "spring".asValue()))
                        assertThat(shouldBeTrue, `is`(true))
                    }

                    @Test
                    fun `test equals with different strings with different index`() {
                        val shouldBeTrue = parser.parse("eq($1, $0)")
                                .evaluate(listOf("spring".asValue(), "spring".asValue()))
                        assertThat(shouldBeTrue, `is`(true))
                    }
                }

                @Nested
                inner class TestLessThan {

                    @Test
                    fun `test lessThan with first number greater than second`() {
                        val shouldBeTrue = parser.parse("lt($0,$1)")
                                .evaluate(listOf(5.asValue(), 10.asValue()))
                        assertThat(shouldBeTrue, `is`(true))
                    }
                }

                @Nested
                inner class TestGreaterThan {

                    @Test
                    fun `test greaterThan with first number greater than second`() {
                        val shouldBeFalse = parser.parse("gt($0,$1)")
                                .evaluate(listOf(5.asValue(), 10.asValue()))
                        assertThat(shouldBeFalse, `is`(false))
                    }
                }
            }
        }
    }

    @Nested
    inner class TestCompoundExpression {

        private val predicate = "not(and(or(lt(10, $0), eq($1, java)), not(gt($2, -5))))"

        @Test
        fun `test compound expression 1`() {
            //                       !((false || true) && !(true))
            //                       !((true) && false)
            //                       !(false)
            //                         true
            val variables = listOf(
                    5.asValue(),
                    "kotlin".asValue(),
                    10.asValue()
            )
            val shouldBeTrue = parser.parse(predicate).evaluate(variables)
            assertThat(shouldBeTrue, `is`(true))
        }

        @Test
        fun `test compound expression 2`() {
            //                       !!((false || true) && !(true))
            //                       !!((true) && false)
            //                       !!(false)
            //                          false
            val variables = listOf(
                    5.asValue(),
                    "kotlin".asValue(),
                    10.asValue()
            )
            val shouldBeFalse = parser.parse("not($predicate)").evaluate(variables)
            assertThat(shouldBeFalse, `is`(false))
        }

        @Test
        fun `test compound expression 3`() {
            //                       !((true || false) && !(false))
            //                       !((true) && true)
            //                       !(true)
            //                         false
            val variables = listOf(
                    "js".asValue(), // unexpected String
                    0.1.asValue(),
                    (-10).asValue()
            )
            assertThrows<java.lang.NumberFormatException> {
                parser.parse(predicate).evaluate(variables)
            }
        }

        @Test
        fun `test compound expression with symbolic references`() {
            //                       !((false || true) && !(true))
            //                       !((true) && false)
            //                       !(false)
            //                         true
            val predicate = "not(and(or(lt(5, \$small_number), eq(\$language.top, java)), not(gte(\$big_number, 10))))"

            val lookupTable = mapOf(
                    "small_number" to 5.asValue(),
                    "language.top" to "kotlin".asValue(),
                    "big_number" to 10.asValue()
            )

            println(parser.parse(predicate).asString())
            val shouldBeTrue = parser.parse(predicate).evaluate { symName -> lookupTable.getValue(symName) }
            assertThat(shouldBeTrue, `is`(true))
        }

    }

    private fun <T : Any> T.asValue(): PredicateValue {
        return PredicateValue(this.toString())
    }
}
