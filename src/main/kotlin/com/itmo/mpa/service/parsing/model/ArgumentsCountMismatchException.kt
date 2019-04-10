package com.itmo.mpa.service.parsing.model

class ArgumentsCountMismatchException(arguments: List<*>, cause: Throwable)
    : RuntimeException(arguments.toString(), cause)
