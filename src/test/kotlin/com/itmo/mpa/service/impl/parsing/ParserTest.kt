package com.itmo.mpa.service.impl.parsing

import com.itmo.mpa.service.impl.parsing.model.*
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
        assertThrows<UnexpectedTokenException> { parser.parse("eq(and)") }
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
            parser.parse("eq($0, $2)").evaluate(listOf(num(0), text("")))
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
                fun `when lessThan is comparing strings they are compared lexicographically`() {
                    val shouldBeTrue = parser.parse("lt(aaa,b)")
                            .evaluate(emptyList())

                    assertThat(shouldBeTrue, `is`(true))
                }

                @Test
                fun `when lessThan is comparing different types number coerced to string`() {
                    val shouldBeTrue = parser.parse("lt(10,2ten)")
                            .evaluate(emptyList())

                    assertThat(shouldBeTrue, `is`(true))
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
                fun `when greaterThan is comparing strings they are compared lexicographically`() {
                    val shouldBeFalse = parser.parse("gt(aaa,b)")
                            .evaluate(emptyList())

                    assertThat(shouldBeFalse, `is`(false))
                }

                @Test
                fun `when greaterThan is comparing different types number coerced to string`() {
                    val shouldBeFalse = parser.parse("gt(10,2ten)")
                            .evaluate(emptyList())

                    assertThat(shouldBeFalse, `is`(false))
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
                                    .evaluate(listOf(num(42)))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `test equals with different numbers`() {
                            val shouldBeFalse = parser.parse("eq($0, 45)")
                                    .evaluate(listOf(num(42)))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test equals with equal strings`() {
                            val shouldBeTrue = parser.parse("eq($0,spring)")
                                    .evaluate(listOf(text("spring")))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `test equals with different strings`() {
                            val shouldBeFalse = parser.parse("eq($0,javascript)")
                                    .evaluate(listOf(text("amazing")))
                            assertThat(shouldBeFalse, `is`(false))
                        }
                    }

                    @Nested
                    inner class TestLessThan {

                        @Test
                        fun `test lessThan with equal numbers`() {
                            val shouldBeFalse = parser.parse("lt($0, 100)")
                                    .evaluate(listOf(num(100)))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test lessThan with first number greater than second`() {
                            val shouldBeFalse = parser.parse("lt($0, -300)")
                                    .evaluate(listOf(num(-200)))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test lessThan with first number less than second`() {
                            val shouldBeTrue = parser.parse("lt($0, 12)")
                                    .evaluate(listOf(num(11)))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `when lessThan is comparing strings they are compared lexicographically`() {
                            val shouldBeTrue = parser.parse("lt($0, b)")
                                    .evaluate(listOf(text("aaa")))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `when lessThan is comparing different types number coerced to string`() {
                            val shouldBeTrue = parser.parse("lt($0, 2ten)")
                                    .evaluate(listOf(text("10")))
                            assertThat(shouldBeTrue, `is`(true))
                        }
                    }

                    @Nested
                    inner class TestGreaterThan {

                        @Test
                        fun `test greaterThan with equal numbers`() {
                            val shouldBeFalse = parser.parse("gt($0,1.3)")
                                    .evaluate(listOf(num(1.3)))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `test greaterThan with first number greater than second`() {
                            val shouldBeTrue = parser.parse("gt($0,-300)")
                                    .evaluate(listOf(num(-200)))
                            assertThat(shouldBeTrue, `is`(true))
                        }

                        @Test
                        fun `test greaterThan with first number less than second`() {
                            val shouldBeFalse = parser.parse("gt($0, 12)")
                                    .evaluate(listOf(num(0.5)))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `when greaterThan is comparing strings they are compared lexicographically`() {
                            val shouldBeFalse = parser.parse("gt($0, b)")
                                    .evaluate(listOf(text("aaa")))
                            assertThat(shouldBeFalse, `is`(false))
                        }

                        @Test
                        fun `when greaterThan is comparing different types number coerced to string`() {
                            val shouldBeFalse = parser.parse("gt(10,2ten)")
                                    .evaluate(listOf(text("10")))
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
                                    .evaluate(listOf(num(42)))
                            assertThat(shouldBeTrue, `is`(true))
                        }
                    }

                    @Nested
                    inner class TestLessThan {

                        @Test
                        fun `test lessThan with first number greater than second`() {
                            val shouldBeFalse = parser.parse("lt(10,$0)")
                                    .evaluate(listOf(num(5)))
                            assertThat(shouldBeFalse, `is`(false))
                        }
                    }

                    @Nested
                    inner class TestGreaterThan {

                        @Test
                        fun `test greaterThan with first number greater than second`() {
                            val shouldBeTrue = parser.parse("gt(10,$0)")
                                    .evaluate(listOf(num(5)))
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
                                .evaluate(listOf(num(42)))
                        assertThat(shouldBeTrue, `is`(true))
                    }

                    @Test
                    fun `test equals with equal numbers with different index`() {
                        val shouldBeTrue = parser.parse("eq($1, $0)")
                                .evaluate(listOf(num(42), num(42)))
                        assertThat(shouldBeTrue, `is`(true))
                    }

                    @Test
                    fun `test equals with different numbers with different index`() {
                        val shouldBeFalse = parser.parse("eq($1, $0)")
                                .evaluate(listOf(num(142), num(42)))
                        assertThat(shouldBeFalse, `is`(false))
                    }

                    @Test
                    fun `test equals with equal strings`() {
                        val shouldBeTrue = parser.parse("eq($0, $0)")
                                .evaluate(listOf(text("spring"), text("spring")))
                        assertThat(shouldBeTrue, `is`(true))
                    }

                    @Test
                    fun `test equals with different strings with different index`() {
                        val shouldBeTrue = parser.parse("eq($1, $0)")
                                .evaluate(listOf(text("spring"), text("spring")))
                        assertThat(shouldBeTrue, `is`(true))
                    }
                }

                @Nested
                inner class TestLessThan {

                    @Test
                    fun `test lessThan with first number greater than second`() {
                        val shouldBeTrue = parser.parse("lt($0,$1)")
                                .evaluate(listOf(num(5), num(10)))
                        assertThat(shouldBeTrue, `is`(true))
                    }
                }

                @Nested
                inner class TestGreaterThan {

                    @Test
                    fun `test greaterThan with first number greater than second`() {
                        val shouldBeFalse = parser.parse("gt($0,$1)")
                                .evaluate(listOf(num(5), num(10)))
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
                    num(5),
                    text("kotlin"),
                    num(10)
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
                    num(5),
                    text("kotlin"),
                    num(10)
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
                    text("js"),
                    num(0.1),
                    num(-10)
            )
            val shouldBeFalse = parser.parse(predicate).evaluate(variables)
            assertThat(shouldBeFalse, `is`(false))
        }
    }

    private fun <T> num(number: T): Either<Double, String>
            where T : Comparable<T>, T : Number {
        return Numerical(number.toDouble())
    }

    private fun <T> text(obj: T): Either<Double, String>
            where T : Comparable<T>, T : CharSequence {
        return Textual(obj.toString())
    }
}
