package com.itmo.mpa.repository

import com.itmo.mpa.entity.medicine.Medicine
import org.springframework.data.repository.CrudRepository

interface MedicineRepository : CrudRepository<Medicine, Long>
