package com.itmo.mpa.repository

import com.itmo.mpa.entity.states.State
import com.itmo.mpa.entity.states.Transition
import org.springframework.data.repository.CrudRepository

interface TransitionRepository : CrudRepository<Transition, Long> {

    fun findByStateFrom(stateFrom: State): List<Transition>

    fun findByStateFromIn(stateFrom: List<State>): List<Transition>
}
