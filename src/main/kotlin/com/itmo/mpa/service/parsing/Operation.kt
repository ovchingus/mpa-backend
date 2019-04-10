package com.itmo.mpa.service.parsing

enum class Operation(
        val token: String,
        val type: OperationType
) {

    NOT("not", UnaryOperation),

    AND("and", LogicalOperation),
    OR("or", LogicalOperation),

    GT("gt", ComparisonOperation),
    LT("lt", ComparisonOperation),
    EQ("eq", ComparisonOperation);
}

sealed class OperationType

object UnaryOperation : OperationType()
sealed class BinaryOperation : OperationType()

object ComparisonOperation : BinaryOperation()
object LogicalOperation : BinaryOperation()
