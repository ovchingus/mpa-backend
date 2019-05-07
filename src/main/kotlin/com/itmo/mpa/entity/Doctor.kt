package com.itmo.mpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "doctor")
class Doctor : LongIdEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String
}
