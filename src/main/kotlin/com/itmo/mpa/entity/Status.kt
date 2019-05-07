package com.itmo.mpa.entity

import java.time.Instant
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

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

    @Column(name = "submit_date", nullable = false)
    lateinit var submittedOn: Instant

    @OneToMany(mappedBy = "status", cascade = [CascadeType.ALL])
    lateinit var diseaseAttributeValues: Set<DiseaseAttributeValue>

    @Column(name = "is_draft", nullable = false)
    var isDraft: Boolean = true

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    lateinit var state: State

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "prescription",
            joinColumns = [JoinColumn(name = "status_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "medicine_id", nullable = false)])
    lateinit var medicines: List<Medicine>
}