package com.itmo.mpa.repository

import com.itmo.mpa.entity.Doctor
import org.springframework.data.repository.CrudRepository

interface DoctorRepository : CrudRepository<Doctor, Long> {

    fun findByNameContaining(name: String): List<Doctor>
}