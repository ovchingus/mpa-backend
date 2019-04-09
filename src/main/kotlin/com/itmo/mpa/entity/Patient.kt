package com.itmo.mpa.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "patient")
class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "age", nullable = false)
    var age: Int = 0

    @Column(name = "BirthDate", nullable = false)
    lateinit var birthDate: Date

    @OneToOne
    @JoinColumn(name = "status_id")
    var status: Status? = null
}