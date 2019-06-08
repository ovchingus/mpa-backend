package com.itmo.mpa.service.impl

import arrow.core.getOrElse
import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.entity.Association
import com.itmo.mpa.entity.AssociationType
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.AssociationRepository
import com.itmo.mpa.service.AssociationService
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.exception.AssociationNotFoundException
import com.itmo.mpa.service.exception.AssociationTypeNotFoundException
import com.itmo.mpa.service.impl.entityservice.DoctorEntityService
import com.itmo.mpa.service.impl.entityservice.PatientStatusEntityService
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AssociationServiceImpl(
        private val doctorEntityService: DoctorEntityService,
        private val associationRepository: AssociationRepository,
        private val predicateService: PredicateService,
        private val patientStatusEntityService: PatientStatusEntityService
) : AssociationService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getDoctorsAssociations(doctorId: Long): List<AssociationResponse> {
        val doctor = doctorEntityService.findById(doctorId)
        return associationRepository.findByDoctor(doctor)
                .map { it.toResponse() }
    }

    override fun getRelevantAssociations(patientId: Long): List<AssociationResponse> {
        val (draft, patient) = patientStatusEntityService.findDraftWithPatient(patientId)
        return associationRepository.findByDoctor(patient.doctor)
                .filter { it.isRelevant(patient, draft) }
                .map { it.toResponse() }
    }

    override fun createAssociation(doctorId: Long, request: AssociationRequest): AssociationResponse {
        val associationType = requireAssociationType(request)
        predicateService.checkPredicate(request.predicate!!)
        val doctor = doctorEntityService.findById(doctorId)
        val association = request.toEntity(doctor)
        association.createdDate = Instant.now()
        association.associationType = associationType
        return associationRepository.save(association)
                .toResponse()
    }

    override fun replaceAssociation(
            associationId: Long,
            request: AssociationRequest
    ): AssociationResponse {
        predicateService.checkPredicate(request.predicate!!)
        val association = associationRepository.findByIdOrNull(associationId)
                ?: throw AssociationNotFoundException(associationId)
        association.text = request.text!!
        association.predicate = request.predicate
        association.associationType = requireAssociationType(request)
        return associationRepository.save(association)
                .toResponse()
    }

    override fun deleteAssociation(associationId: Long) {
        val association = associationRepository.findByIdOrNull(associationId)
                ?: throw AssociationNotFoundException(associationId)
        associationRepository.delete(association)
    }

    private fun requireAssociationType(request: AssociationRequest): AssociationType {
        return AssociationType.values()
                .find { it.name.equals(request.associationType, ignoreCase = true) }
                ?: throw AssociationTypeNotFoundException(request.associationType)
    }

    private fun Association.isRelevant(patient: Patient?, draft: Status?): Boolean {
        return predicateService.testPredicate(patient, draft, predicate)
                .getOrElse { false }
    }
}
