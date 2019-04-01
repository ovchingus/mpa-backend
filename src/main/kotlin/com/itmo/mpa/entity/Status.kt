package com.itmo.mpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "status")
class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "description", nullable = false)
    lateinit var description: String

}