package com.itmo.mpa.repository

import com.itmo.mpa.model.Draft
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.HashMap

@Repository
class DraftStubRepository : DraftRepository {

    private val storage = HashMap<Int, Draft>()

    private var counter = 0

    override fun findAll(): Iterable<Draft> = storage.values

    override fun findById(id: Int): Optional<Draft> = Optional.ofNullable(storage[id])

    override fun <S : Draft> save(draft: S): S {
        val id = draft.id
        if (id == null) {
            counter++
            storage[counter] = draft
            draft.id = counter
        } else {
            storage[id] = draft
        }

        return draft
    }
}