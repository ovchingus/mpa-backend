package com.itmo.mpa.repository

import com.itmo.mpa.entity.DiseaseAttribute
import org.springframework.data.repository.CrudRepository

interface DiseaseAttributeRepository: CrudRepository<Long, DiseaseAttribute>