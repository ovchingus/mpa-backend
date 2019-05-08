package com.itmo.mpa.repository

import com.itmo.mpa.entity.DiseaseAttributeValue
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

interface DiseaseAttributeValueRepository: CrudRepository<Serializable, DiseaseAttributeValue>
