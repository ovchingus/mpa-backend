package com.itmo.mpa.service

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.model.Status
import com.itmo.mpa.repository.DraftRepository
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.mapping.toDto
import com.itmo.mpa.service.mapping.toModel
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatusServiceImpl(private val patientRepository: PatientRepository, private val draftRepository: DraftRepository) : StatusService {

    override fun commitDraft(id: Int) {
        val draft = draftRepository.findById(id).orElseThrow { NotFoundException("Draft for patient with id $id not found")  }
        patientRepository.findById(id).get().status = Status(draft.draft)
    }


    override fun createDraft(id: Int, draftRequest: DraftRequest) {
        val draft = draftRequest.toModel()
        draft.id = id

        draftRepository.save(draft)
    }

    override fun findStatus(id: Int): StatusResponse? {
        val patient = patientRepository.findById(id).orElseThrow { NotFoundException("Patient $id not found") }
        return Optional.ofNullable(patient.status).map { it.toDto() }.orElse(null)
    }

    override fun findStatusDraft(id: Int): DraftResponse? {
        return draftRepository.findById(id).map { it.toDto() }.orElse(null)
    }
}