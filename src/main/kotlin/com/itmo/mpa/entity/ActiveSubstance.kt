package com.itmo.mpa.entity

import javax.persistence.*

@Entity
@Table(name = "active_substance")
class ActiveSubstance : LongIdEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(name = "active_substance_in_medicine",
            joinColumns = [JoinColumn(name = "active_substance_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "medicine_id", nullable = false)])
    lateinit var medicines: Set<Medicine>
}
