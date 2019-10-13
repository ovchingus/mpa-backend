package com.itmo.mpa.entity

import com.itmo.mpa.entity.medicine.Medicine
import javax.persistence.*

@Entity
@Table(name = "disease")
class Disease : LongIdEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(name = "medicine_for_diseases",
            joinColumns = [JoinColumn(name = "disease_id", nullable = false)],
            inverseJoinColumns = [JoinColumn(name = "medicine_id", nullable = false)])
    lateinit var medicines: Set<Medicine>
}
