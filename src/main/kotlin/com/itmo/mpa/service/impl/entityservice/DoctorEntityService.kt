package com.itmo.mpa.service.impl.entityservice

import com.itmo.mpa.entity.Doctor
import com.itmo.mpa.repository.DoctorRepository
import com.itmo.mpa.service.exception.DoctorNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DoctorEntityService(
    private val doctorRepository: DoctorRepository
) {

    fun findById(id: Long): Doctor {
        return doctorRepository.findByIdOrNull(id) ?: throw DoctorNotFoundException(id)
    }
}
