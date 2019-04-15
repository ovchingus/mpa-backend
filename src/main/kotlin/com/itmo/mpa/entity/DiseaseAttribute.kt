package com.itmo.mpa.entity

import javax.persistence.*

@Entity
@Table(name = "DiseaseAttributes")
class DiseaseAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    lateinit var name: String

    @Column(nullable = false)
    lateinit var type: String

    @Enumerated
    @Column(nullable = false)
    lateinit var requirementType: RequirementType

    @Column(nullable = false)
    var requirementId: Long = 0

    @Column(name = "isRequired", nullable = false)
    var required: Boolean = true
}