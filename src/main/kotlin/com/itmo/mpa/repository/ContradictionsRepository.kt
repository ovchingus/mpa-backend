package com.itmo.mpa.repository

import com.itmo.mpa.entity.Contradiction
import org.springframework.data.repository.CrudRepository

interface ContradictionsRepository : CrudRepository<Contradiction, Long>
