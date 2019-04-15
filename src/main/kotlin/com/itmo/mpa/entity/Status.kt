package com.itmo.mpa.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "status")
class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    lateinit var patient: Patient

    @Column(name = "submittedOn", nullable = false)
    lateinit var submittedOn: Instant

    @OneToMany(mappedBy = "status", cascade = [CascadeType.ALL])
    lateinit var diseaseAttributeValues: Set<DiseaseAttributeValue>

    @Column(name = "draft", nullable = false)
    var draft: Boolean = true

    @ManyToOne
    @JoinColumn(name = "states_id")
    var state: State? = null
}