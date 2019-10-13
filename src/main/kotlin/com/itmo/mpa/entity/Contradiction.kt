package com.itmo.mpa.entity

import com.itmo.mpa.entity.medicine.Medicine
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "contradictions")
class Contradiction : LongIdEntity() {

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
