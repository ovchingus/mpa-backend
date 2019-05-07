package com.itmo.mpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "transition")
class Transition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "state_from_id", nullable = false)
    lateinit var stateFrom: State

    @ManyToOne
    @JoinColumn(name = "state_to_id", nullable = false)
    lateinit var stateTo: State

    @Column(name = "predicate")
    lateinit var predicate: String

}