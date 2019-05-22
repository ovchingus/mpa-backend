package com.itmo.mpa.service.impl.predicate.resolver

interface SymbolicNameResolver {

    /**
     *  Resolves given name
     *
     *  @param parameters reference values to perform resolving
     *  @param name symbolic link to resolve
     *  @return resolved value
     *  @throws ResolvingException if value couldn't be resolved with this resolver
     */
    fun resolve(parameters: ResolvingParameters, name: String): String

    /**
     *  Checks if a given [name] is supported by current resolver
     *
     *  @param name symbolic link to check
     *  @return true if given name is supported by resolver
     */
    fun isSupported(name: String): Boolean
}
