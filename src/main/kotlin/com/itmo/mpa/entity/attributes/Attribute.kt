package com.itmo.mpa.entity.attributes

import com.itmo.mpa.entity.LongIdEntity
import javax.persistence.*

@Entity
@Table(name = "attributes")
class Attribute : LongIdEntity() {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "attribute", cascade = [CascadeType.ALL])
    var possibleValues: Set<AttributeValue> = emptySet()

    @Column(name = "name", nullable = false)
    lateinit var name: String

    @Column(name = "type", nullable = false)
    lateinit var type: String
}
