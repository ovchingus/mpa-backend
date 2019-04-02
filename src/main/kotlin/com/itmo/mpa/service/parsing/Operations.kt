package com.itmo.mpa.service.parsing

enum class Operations(val token: String) {

    NOT("not"),
    AND("and"),
    OR("or"),
    GT("gt"),
    LT("lt"),
    EQ("eq");
}
