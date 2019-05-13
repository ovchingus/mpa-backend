package com.itmo.mpa.repository

import com.itmo.mpa.entity.DiseaseAttribute
import com.itmo.mpa.entity.RequirementType
import org.springframework.data.repository.CrudRepository

interface DiseaseAttributeRepository : CrudRepository<DiseaseAttribute, Long> {

    fun findAllByAttributeIdIn(ids: Collection<Long>): List<DiseaseAttribute>

    fun findByRequirementTypeAndRequirementId(
            requirementType: RequirementType,
            requirementId: Long
    ): List<DiseaseAttribute>
}
