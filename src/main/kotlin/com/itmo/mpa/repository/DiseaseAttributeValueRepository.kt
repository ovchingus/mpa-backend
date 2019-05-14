package com.itmo.mpa.repository

import com.itmo.mpa.entity.DiseaseAttributeValue
import com.itmo.mpa.entity.Status
import org.springframework.data.repository.CrudRepository

interface DiseaseAttributeValueRepository : CrudRepository<DiseaseAttributeValue, Long> {

    fun deleteALlByStatus(status: Status)
}