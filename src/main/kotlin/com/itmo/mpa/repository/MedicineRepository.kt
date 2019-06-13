package com.itmo.mpa.repository

import com.itmo.mpa.entity.Medicine
import org.springframework.data.repository.CrudRepository

interface MedicineRepository : CrudRepository<Medicine, Long>
