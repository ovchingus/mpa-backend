package com.itmo.mpa.repository

import com.itmo.mpa.entity.Disease
import com.itmo.mpa.entity.State
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StateRepository : CrudRepository<State, Long> {

    @Query("SELECT s FROM State s WHERE s.disease = :disease AND s NOT IN (SELECT t.stateTo FROM Transition t)")
    fun findInitialState(disease: Disease): State?
}
