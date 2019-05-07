package com.itmo.mpa.entity

import javax.persistence.*

@Entity
@Table(name = "medicine")
class Medicine : LongIdEntity()  {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "medicine_for_diseases",
            joinColumns = [JoinColumn(name = "medicine_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "disease_id", nullable = false)])
    lateinit var diseases: Set<Disease>

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "prescription",
            joinColumns = [JoinColumn(name = "medicine_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "status_id", nullable = false)])
    lateinit var statuses: Set<Status>

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(name = "active_substance_in_medicine",
            joinColumns = [JoinColumn(name = "medicine_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "active_substance_id", nullable = false)])
    lateinit var activeSubstance: Set<ActiveSubstance>
}