package com.itmo.mpa.repository

import java.util.*


/**
 *  Copy-pasted spring data interface
 */
@Deprecated(
        "Use one from spring when it's ready",
        ReplaceWith("CrudRepository<your_type, your_id_type>", "org.springframework.data.repository")
)
interface CrudRepository<T, ID> {

    fun findAll(): Iterable<T>

    fun findById(id: ID): Optional<T>

    fun <S : T> save(entity: S): S
}