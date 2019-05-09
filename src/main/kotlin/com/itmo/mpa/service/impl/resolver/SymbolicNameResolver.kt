package com.itmo.mpa.service.impl.resolver

interface SymbolicNameResolver {

    /**
     *  Resolves given name
     *
     *  @param parameters reference values to perform resolving
     *  @param name symbolic link to resolve
     *  @return resolved value, or `null` if value could not be resolved
     */
    fun resolve(parameters: ResolvingParameters, name: String): String?
}