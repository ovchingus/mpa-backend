package com.itmo.mpa.repository

import com.itmo.mpa.entity.State
import com.itmo.mpa.entity.Transition
import org.springframework.data.repository.CrudRepository

interface TransitionRepository : CrudRepository<Transition, Long> {

    fun findByStateFrom(stateFrom: State): List<Transition>
}