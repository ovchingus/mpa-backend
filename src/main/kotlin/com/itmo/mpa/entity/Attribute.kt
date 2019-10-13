package com.itmo.mpa.entity

import javax.persistence.*

@Entity
@Table(name = "Attributes")
class Attribute : LongIdEntity() {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "attribute", cascade = [CascadeType.ALL])
    var possibleValues: Set<AttributeValue> = emptySet()

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "type", nullable = false)
    lateinit var type: String
}
