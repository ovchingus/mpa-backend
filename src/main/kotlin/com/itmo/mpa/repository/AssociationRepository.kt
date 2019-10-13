package com.itmo.mpa.repository

import com.itmo.mpa.entity.associations.Association
import com.itmo.mpa.entity.Doctor
import org.springframework.data.repository.CrudRepository

interface AssociationRepository : CrudRepository<Association, Long> {

    fun findByDoctor(doctor: Doctor): List<Association>

    fun findByIdAndDoctor(id: Long, doctor: Doctor): Association?
}
