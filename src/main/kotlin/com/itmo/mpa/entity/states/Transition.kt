package com.itmo.mpa.entity.states

import com.itmo.mpa.entity.LongIdEntity
import javax.persistence.*

@Entity
@Table(name = "transition")
class Transition : LongIdEntity() {

    @ManyToOne
    @JoinColumn(name = "state_from_id", nullable = false)
    lateinit var stateFrom: State

    @ManyToOne
    @JoinColumn(name = "state_to_id", nullable = false)
    lateinit var stateTo: State

    @Column(name = "predicate")
    lateinit var predicate: String

    @Column(name = "description")
    lateinit var description: String
}
