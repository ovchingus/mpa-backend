package com.itmo.mpa.service.impl.parsing.model

class ArgumentsCountMismatchException(arguments: List<*>, cause: Throwable)
    : RuntimeException(arguments.toString(), cause)
