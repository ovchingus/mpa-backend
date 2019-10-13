package com.itmo.mpa.entity.states

import com.itmo.mpa.entity.Disease
import com.itmo.mpa.entity.LongIdEntity
import javax.persistence.*

@Entity
@Table(name = "state")
class State : LongIdEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

    @ManyToOne
    @JoinColumn(name = "disease_id", nullable = false)
    lateinit var disease: Disease
}
