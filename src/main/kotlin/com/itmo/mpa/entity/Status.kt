package com.itmo.mpa.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "status")
class Status : LongIdEntity() {

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    lateinit var patient: Patient

    @Column(name = "submit_date", nullable = false)
    lateinit var submittedOn: Instant

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "status", cascade = [CascadeType.ALL])
    var diseaseAttributeValues: Set<DiseaseAttributeValue> = emptySet()

    @Column(name = "is_draft", nullable = false)
    var draft: Boolean = true

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    lateinit var state: State

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(name = "prescription",
            joinColumns = [JoinColumn(name = "status_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "medicine_id", nullable = false)])
    var medicines: Set<Medicine> = emptySet()
}
