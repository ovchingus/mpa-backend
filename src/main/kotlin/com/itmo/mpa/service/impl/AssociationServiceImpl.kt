package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.entity.Association
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.AssociationRepository
import com.itmo.mpa.service.AssociationService
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.exception.AssociationNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AssociationServiceImpl(
        private val doctorEntityService: DoctorEntityService,
        private val associationRepository: AssociationRepository,
        private val predicateService: PredicateService,
        private val patientStatusEntityService: PatientStatusEntityService
) : AssociationService {

    override fun getDoctorsAssociations(doctorId: Long, patientId: Long?): List<AssociationResponse> {
        val doctor = doctorEntityService.findById(doctorId)
        val (draft, patient) = patientStatusEntityService.findDraftWithPatient(patientId)
        return associationRepository.findByDoctor(doctor)
                .map { it.formResponse(patient, draft) }
    }

    override fun createAssociation(doctorId: Long, request: AssociationRequest): AssociationResponse {
        val doctor = doctorEntityService.findById(doctorId)
        val association = request.toEntity()
        association.createdDate = Instant.now()
        association.doctor = doctor
        return associationRepository.save(association)
                .formResponse(patient = null, draft = null)
    }

    override fun replaceAssociation(doctorId: Long, associationId: Long, request: AssociationRequest): AssociationResponse {
        val doctor = doctorEntityService.findById(doctorId)
        val association = associationRepository.findByIdAndDoctor(associationId, doctor)
                ?: throw AssociationNotFoundException(associationId)
        association.text = request.text!!
        return associationRepository.save(association)
                .formResponse(patient = null, draft = null)
    }

    override fun deleteAssociation(doctorId: Long, associationId: Long) {
        val doctor = doctorEntityService.findById(doctorId)
        val association = associationRepository.findByIdAndDoctor(associationId, doctor)
                ?: throw AssociationNotFoundException(associationId)
        associationRepository.delete(association)
    }

    private fun Association.formResponse(
            patient: Patient?,
            draft: Status?
    ): AssociationResponse {
        return try {
            val isRelevant = predicateService.testPredicate(patient, draft, predicate)
            AssociationResponse(id, text, createdDate, isRelevant, errorCause = null)
        } catch (e: Exception) {
            AssociationResponse(id, text, createdDate, isRelevant = null, errorCause = e.message)
        }
    }
}
