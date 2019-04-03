package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.entity.Draft
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.repository.DraftRepository
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.NotFoundException
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StatusServiceImpl(
        private val patientRepository: PatientRepository,
        private val draftRepository: DraftRepository
) : StatusService {

    override fun commitDraft(patientId: Long) {
        // todo
        //val draft = draftRepository.findById(patientId).orElseThrow { NotFoundException("Draft for patient with id $patientId not found") }
        //patientRepository.findById(patientId).get().status = Status(draft.draft)
    }

    override fun rewriteDraft(patientId: Long, draftRequest: DraftRequest) {
        val (oldDraft, patient) = findDraftWithPatient(patientId)
        if (oldDraft != null) {
            draftRepository.delete(oldDraft)
        }
        draftRepository.save(draftRequest.toEntity(patient))
    }

    override fun findDraft(patientId: Long): DraftResponse? {
        val (draft) = findDraftWithPatient(patientId)
        return draft?.toResponse()
    }

    private fun findDraftWithPatient(patientId: Long): Pair<Draft?, Patient> {
        val patient = patientRepository.findByIdOrNull(patientId) ?: throw NotFoundException("Patient $patientId not found")
        return Pair(draftRepository.findDraftByPatient(patient), patient)
    }
}