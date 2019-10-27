package com.itmo.mpa.repository

import com.itmo.mpa.entity.Status
import com.itmo.mpa.entity.attributes.DiseaseAttributeValue
import org.springframework.data.repository.CrudRepository

interface DiseaseAttributeValueRepository : CrudRepository<DiseaseAttributeValue, Long> {

    fun deleteAllByStatus(status: Status)
}
