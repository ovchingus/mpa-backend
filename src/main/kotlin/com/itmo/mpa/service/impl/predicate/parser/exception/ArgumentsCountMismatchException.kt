package com.itmo.mpa.service.impl.predicate.parser.exception

class ArgumentsCountMismatchException(arguments: List<*>, cause: Throwable)
    : RuntimeException(arguments.toString(), cause)
