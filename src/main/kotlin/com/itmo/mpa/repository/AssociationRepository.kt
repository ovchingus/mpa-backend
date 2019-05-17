package com.itmo.mpa.repository

import com.itmo.mpa.entity.Association
import org.springframework.data.repository.CrudRepository

interface AssociationRepository : CrudRepository<Association, Long> {

    fun findByDoctorId(doctorId: Long): List<Association>
}
