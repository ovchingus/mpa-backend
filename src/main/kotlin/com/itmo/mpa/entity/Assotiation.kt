package com.itmo.mpa.entity

import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "association")
class Assotiation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "created_date", nullable = false)
    lateinit var createdDate: Instant

    @Column(name = "predicate", nullable = false)
    lateinit var predicate: String

    @Column(name = "text", nullable = false)
    lateinit var text: String

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    lateinit var doctor: Doctor

}