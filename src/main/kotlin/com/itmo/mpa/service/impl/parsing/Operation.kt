package com.itmo.mpa.service.impl.parsing

enum class Operation(
        val token: String,
        val type: OperationType
) {

    NOT("not", UnaryOperation),

    AND("and", LogicalOperation),
    OR("or", LogicalOperation),

    GT("gt", ComparisonOperation),
    GTE("gte", ComparisonOperation),
    LT("lt", ComparisonOperation),
    LTE("lte", ComparisonOperation),
    EQ("eq", ComparisonOperation),
    HAS("has", ComparisonOperation);
}

sealed class OperationType

object UnaryOperation : OperationType()
sealed class BinaryOperation : OperationType()

object ComparisonOperation : BinaryOperation()
object LogicalOperation : BinaryOperation()
