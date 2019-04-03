package com.itmo.mpa.entity

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

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    var status: Status? = null
}