package com.itmo.mpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Attributes")
class Attribute : LongIdEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "type", nullable = false)
    lateinit var type: String
}
