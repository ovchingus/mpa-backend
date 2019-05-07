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
@Table(name = "contraindications")
class Contraindications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "created_date", nullable = false)
    lateinit var createdDate: Instant

    @Column(name = "predicate", nullable = false)
    lateinit var predicate: String

    @Column(name = "source", nullable = false)
    lateinit var source: String

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    lateinit var medicine: Medicine

}