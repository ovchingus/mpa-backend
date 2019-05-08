package com.itmo.mpa.entity

import java.io.Serializable
import javax.persistence.*

@Table(name = "DiseaseAttributeValues")
@Entity
class DiseaseAttributeValue : Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    lateinit var status: Status

    @Id
    @ManyToOne
    @JoinColumn
    lateinit var diseaseAttribute: DiseaseAttribute

    @Column(nullable = false)
    lateinit var value: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DiseaseAttributeValue) return false

        if (status != other.status) return false
        if (diseaseAttribute != other.diseaseAttribute) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + diseaseAttribute.hashCode()
        return result
    }
}
