package com.itmo.mpa.repository

import com.itmo.mpa.entity.Disease
import org.springframework.data.jpa.repository.JpaRepository

interface DiseaseRepository : JpaRepository<Disease, Long>