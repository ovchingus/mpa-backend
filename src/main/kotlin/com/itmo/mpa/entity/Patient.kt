package com.itmo.mpa.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "patient")
class Patient : LongIdEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "birth_date", nullable = false)
    lateinit var birthDate: Instant

    @ManyToOne
    @JoinColumn(name = "disease_id", nullable = true)
    lateinit var disease: Disease

    @OneToOne
    @JoinColumn(name = "current_status_id", nullable = true) // it is nullable in db to fix circular dependency
    lateinit var currentStatus: Status

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    lateinit var doctor: Doctor
}
