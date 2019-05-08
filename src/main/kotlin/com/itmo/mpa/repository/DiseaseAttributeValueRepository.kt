package com.itmo.mpa.repository

import com.itmo.mpa.entity.DiseaseAttributeValue
import org.springframework.data.repository.CrudRepository

interface DiseaseAttributeValueRepository : CrudRepository<DiseaseAttributeValue, Long>