package com.itmo.mpa.entity

import javax.persistence.*

@Entity
@Table(name = "state_image")
class StateImage : LongIdEntity() {

    @Column(name = "stateId", nullable = false)
    var stateId: Long = 0

    @Column(name = "algorithm_position", nullable = false)
    lateinit var algorithmPosition: String

    @Column(name = "picture", nullable = false)
    lateinit var picture: String
}
