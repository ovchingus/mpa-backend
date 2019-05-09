package com.itmo.mpa.entity

import javax.persistence.*

@Table(name = "DiseaseAttributeValues")
@Entity
class DiseaseAttributeValue : LongIdEntity() {

    @ManyToOne
    @JoinColumn
    lateinit var status: Status

    @ManyToOne
    @JoinColumn
    lateinit var diseaseAttribute: DiseaseAttribute

    @Column(nullable = false)
    lateinit var value: String
}
