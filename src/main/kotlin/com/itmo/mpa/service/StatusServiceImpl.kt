package com.itmo.mpa.service

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.mapping.toDto
import java.util.*

class StatusServiceImpl(private val patientRepository: PatientRepository) : StatusService {

    override fun createStatus(statusRequest: StatusRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findStatus(id: Int): StatusResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findStatusDraft(id: Int): DraftResponse? {
        val patient = patientRepository.findById(id).orElseThrow { NotFoundException("Patient $id not found") }
        val status = patient.status
        return Optional.ofNullable(status.draft).map { it.toDto() }.orElse(null)
    }
}