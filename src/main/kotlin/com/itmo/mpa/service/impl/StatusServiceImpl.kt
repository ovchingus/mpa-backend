package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.entity.Draft
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.DraftRepository
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StatusServiceImpl(
        private val patientRepository: PatientRepository,
        private val draftRepository: DraftRepository,
        private val statusRepository: StatusRepository
) : StatusService {

    override fun commitDraft(patientId: Long) {
        val (draft, patient) = findDraftWithPatient(patientId)
        if (draft == null) throw NoPendingDraftException(patientId)
        val status = Status().apply {
            name = draft.name
            description = draft.description
        }
        draftRepository.delete(draft)
        status.patient = patient
        statusRepository.save(status)
        patient.status = status
        patientRepository.save(patient)
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
        val patient = patientRepository.findByIdOrNull(patientId)
                ?: throw PatientNotFoundException(patientId)
        return Pair(draftRepository.findDraftByPatient(patient), patient)
    }
}