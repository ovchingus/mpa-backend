package com.itmo.mpa.service.parsing

import com.itmo.mpa.service.parsing.model.*
import org.springframework.stereotype.Service

@Service
class PredicateStubService : PredicateService {

    override fun parsePredicate(predicate: String): (List<Either<Int, String>>) -> Boolean {

        // !( ( (y gt 100) or ("hello" eq x) ) and (y eq 42) )

        // x="g", y=42
        // !( ( (y gt 100) or ("hello" eq x) ) or (y eq 42) )
        // !( ( (42 gt 100) or ("hello" eq x) ) or (y eq 42) )
        // !( ( (false) or ("hello" eq x) ) or (y eq 42) )
        // !( ( (false) or ("hello" eq "g") ) or (y eq 42) )
        // !( ( (false) or (false) ) ) or (y eq 42) )
        // !( ( false ) ) or (y eq 42) )
        // !( ( false ) ) or (42 eq 42) )
        // !( (false) or (true) )
        // !( true )
        // false

        val expr = Not<Either<Int, String>>(
                Or(
                        Or(
                                GreaterThan(UnknownValue(1), KnownValue(Numerical(100))),
                                Equal(KnownValue(Textual("hello")), UnknownValue(0))
                        ),
                        Equal(UnknownValue(1), KnownValue(Numerical(42)))
                )
        )

        return { expr.evaluate(it) }
    }
}
