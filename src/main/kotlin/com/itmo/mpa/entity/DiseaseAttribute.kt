package com.itmo.mpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "DiseaseAttributes")
class DiseaseAttribute : LongIdEntity() {

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "type", nullable = false)
    lateinit var type: String

    @Enumerated
    @Column(name = "requirement_type_id", nullable = false)
    lateinit var requirementType: RequirementType

    @Column(name = "requirement_id", nullable = false)
    var requirementId: Long = 0

    @Column(name = "is_required ", nullable = false)
    var isRequired: Boolean = true
}