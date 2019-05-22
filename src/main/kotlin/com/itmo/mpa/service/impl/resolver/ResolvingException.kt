package com.itmo.mpa.service.impl.resolver

/**
 *  Exception will be thrown if [SymbolicNameResolver.resolve] could not resolve a reference.
 */
class ResolvingException(
        /**
         *  see [ResolverErrorCode]
         */
        val code: Int,
        val reason: String
) : RuntimeException()
