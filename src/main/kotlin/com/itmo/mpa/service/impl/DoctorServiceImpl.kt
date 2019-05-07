package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.DoctorRequest
import com.itmo.mpa.dto.response.DoctorResponse
import com.itmo.mpa.repository.DoctorRepository
import com.itmo.mpa.service.DoctorService
import com.itmo.mpa.service.exception.DoctorNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DoctorServiceImpl(
        private val doctorRepository: DoctorRepository
) : DoctorService {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    override fun createDoctor(request: DoctorRequest) {
        logger.info("Saving doctor: {}", request)
        doctorRepository.save(request.toEntity())
                .also { logger.debug("Saved doctor: {}", it) }
    }

    override fun findAll(): List<DoctorResponse> {
        logger.debug("Requested all doctors")
        return doctorRepository.findAll()
                .also { logger.debug("all doctors returned: {}", it) }
                .map { it.toResponse() }
    }

    override fun findById(id: Long): DoctorResponse {
        val doctor = doctorRepository.findByIdOrNull(id)
                .also { logger.debug("findById returned: {}", it) }

        return doctor?.toResponse() ?: throw DoctorNotFoundException(id)
    }

    override fun findByName(name: String): List<DoctorResponse> {
        return doctorRepository.findByNameContaining(name)
                .also { logger.debug("findByName {} returned: {}", name, it) }
                .map { it.toResponse() }
    }
}