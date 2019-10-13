package com.itmo.mpa.entity.attributes

import com.itmo.mpa.entity.LongIdEntity
import javax.persistence.*

@Entity
@Table(name = "disease_attributes")
class DiseaseAttribute : LongIdEntity() {

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    lateinit var attribute: Attribute

    @Column(name = "is_required ", nullable = false)
    var isRequired: Boolean = true

    @Enumerated
    @Column(name = "requirement_type_id", nullable = false)
    lateinit var requirementType: RequirementType

    @Column(name = "requirement_id", nullable = false)
    var requirementId: Long = 0
}
