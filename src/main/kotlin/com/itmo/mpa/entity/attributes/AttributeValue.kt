package com.itmo.mpa.entity.attributes

import com.itmo.mpa.entity.LongIdEntity
import javax.persistence.*

@Entity
@Table(name = "attribute_values")
class AttributeValue : LongIdEntity() {

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = false)
    lateinit var attribute: Attribute

    @Column(name = "value", nullable = false)
    lateinit var value: String
}
