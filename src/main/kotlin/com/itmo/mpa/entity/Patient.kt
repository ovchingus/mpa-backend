package com.itmo.mpa.entity

import java.time.Instant
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

    @Column(name = "BirthDate", nullable = false)
    lateinit var birthDate: Instant

    @ManyToOne
    @JoinColumn(name = "disease_id")
    var disease: Disease? = null

    @OneToOne
    @JoinColumn(name = "status_id")
    var status: Status? = null
}