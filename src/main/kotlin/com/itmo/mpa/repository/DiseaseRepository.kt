package com.itmo.mpa.repository

import com.itmo.mpa.entity.Disease
import org.springframework.data.repository.CrudRepository

interface DiseaseRepository : CrudRepository<Disease, Long>