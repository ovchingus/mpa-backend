package com.itmo.mpa.repository

import com.itmo.mpa.entity.Contraindications
import org.springframework.data.repository.CrudRepository

interface ContraindicationsRepository : CrudRepository<Contraindications, Long>
