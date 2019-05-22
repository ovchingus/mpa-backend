package com.itmo.mpa.service.impl.parsing.model

import com.itmo.mpa.service.impl.predicate.parser.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.jupiter.api.Test

class BinaryExpressionKtTest {

    @Test
    fun `test collecting symbolic names`() {
        val predicate = Or(
                And(
                        Equal(KnownValue(""), UnknownValue("ref1")),
                        GreaterThan(UnknownValue("ref2"), UnknownValue("ref1"))
                ),
                Not(
                        And(
                                LessThanEqual(UnknownValue("ref1"), UnknownValue("ref4")),
                                LessThan(UnknownValue("ref3"), KnownValue(""))
                        )
                )
        )
        val references = predicate.collectReferences()
        assertThat(references, containsInAnyOrder("ref1", "ref2", "ref3", "ref4"))
    }
}
