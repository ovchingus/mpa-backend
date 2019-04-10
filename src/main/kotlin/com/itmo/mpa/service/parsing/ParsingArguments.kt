package com.itmo.mpa.service.parsing

/**
 *  Data class to combine repetitive arguments among parser methods
 */
data class ParsingArguments(
        val operation: Operation,
        val expression: String,
        val begin: Int,
        val end: Int
)