package com.itmo.mpa.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.itmo.mpa.util.MyCustomDeserializer
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "patient")
class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @JsonDeserialize(using = MyCustomDeserializer::class)
    @Column(name = "birth_date", nullable = false)
    lateinit var birthDate: Instant

    @ManyToOne
    @JoinColumn(name = "disease_id", nullable = true)
    var disease: Disease? = null

    @OneToOne
    @JoinColumn(name = "status_id", nullable = true)
    var status: Status? = null
}