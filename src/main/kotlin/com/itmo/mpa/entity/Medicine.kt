package com.itmo.mpa.entity

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
import javax.persistence.Table

@Entity
@Table(name = "medicine")
class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "medicine_for_diseases",
            joinColumns = [JoinColumn(name = "medicine_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "disease_id", nullable = false)])
    lateinit var diseases: List<Disease>

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "prescription",
            joinColumns = [JoinColumn(name = "medicine_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "status_id", nullable = false)])
    lateinit var statuses: List<Status>

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "active_substance_in_medicine",
            joinColumns = [JoinColumn(name = "medicine_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "active_substance_id", nullable = false)])
    lateinit var activeSubstance: List<ActiveSubstance>

}