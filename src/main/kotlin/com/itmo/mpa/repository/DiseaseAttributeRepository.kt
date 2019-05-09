package com.itmo.mpa.repository

import com.itmo.mpa.entity.Attribute
import com.itmo.mpa.entity.DiseaseAttribute
import com.itmo.mpa.entity.RequirementType
import org.springframework.data.repository.CrudRepository

interface DiseaseAttributeRepository : CrudRepository<DiseaseAttribute, Long> {

    fun findByAttributeAndRequirementTypeAndRequirementId(
            attribute: Attribute,
            requirementType: RequirementType,
            requirementId: Long
    ): DiseaseAttribute?
}
