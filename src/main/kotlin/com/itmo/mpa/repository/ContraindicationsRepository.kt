package com.itmo.mpa.repository

import com.itmo.mpa.entity.Contraindications
import com.itmo.mpa.entity.Medicine
import org.springframework.data.repository.CrudRepository

interface ContraindicationsRepository : CrudRepository<Contraindications, Long> {

    fun findByMedicine(medicine: Medicine): List<Contraindications>

    fun findByMedicineIn(medicines: Collection<Medicine>): List<Contraindications>
}