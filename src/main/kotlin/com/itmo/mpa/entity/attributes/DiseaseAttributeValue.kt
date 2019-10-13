package com.itmo.mpa.entity.attributes

import com.itmo.mpa.entity.LongIdEntity
import com.itmo.mpa.entity.Status
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
