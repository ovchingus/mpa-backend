package com.itmo.mpa.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "contraindications")
class Contraindications : LongIdEntity() {

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