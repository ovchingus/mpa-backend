package com.itmo.mpa.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "status")
class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

    @ManyToOne
    @JoinColumn(name = "patient_id")
    var patient: Patient? = null

    @Column(name = "submittedOn", nullable = false)
    lateinit var submittedOn: Date

    @Column(name = "isDraft", nullable = false)
    var isDraft: Boolean = true

    @ManyToOne
    @JoinColumn(name = "states_id")
    lateinit var stateId: State
}