package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.entity.Medicine
import com.itmo.mpa.repository.MedicineRepository
import com.itmo.mpa.repository.StateRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.DraftService
import com.itmo.mpa.service.exception.MedicineNotFoundException
import com.itmo.mpa.service.exception.StateNotFoundException
import com.itmo.mpa.service.mapping.toEntity
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class DraftServiceImpl(
        private val statusRepository: StatusRepository,
        private val stateRepository: StateRepository,
        private val medicineRepository: MedicineRepository,
        private val patientStatusEntityService: PatientStatusEntityService,
        private val attributesEntityService: DiseaseAttributesEntityService
) : DraftService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun rewriteDraft(patientId: Long, statusDraftRequest: StatusRequest) {
        logger.info("rewriteDraft: change existing draft or create one for patient with id - $patientId, " +
                "by status draft request - {}", statusDraftRequest)

        val (oldDraft, patient) = patientStatusEntityService.findDraftWithPatient(patientId)

        val state = stateRepository.findByIdOrNull(statusDraftRequest.stateId!!)
                ?: throw StateNotFoundException(statusDraftRequest.stateId)

        var statusEntity = oldDraft ?: statusDraftRequest.toEntity(patient, state)
        statusEntity.submittedOn = Instant.now()
        statusEntity.medicines = statusDraftRequest.medicines.mapTo(HashSet()) { requireMedicine(it) }
        statusEntity = statusRepository.save(statusEntity)

        statusEntity.diseaseAttributeValues = attributesEntityService
                .replaceAttributeValues(statusEntity, statusDraftRequest.attributes)

        statusRepository.save(statusEntity)
    }

    override fun findDraft(patientId: Long): StatusResponse {
        logger.info("findDraft: find draft by patient id - $patientId")
        return patientStatusEntityService.requireDraftWithPatient(patientId).first
                .toResponse()
                .also { logger.info("findDraft: result {}", it) }
    }

    override fun getDiseaseAttributes(patientId: Long): List<DiseaseAttributeResponse> {
        return attributesEntityService.getDiseaseAttributes(patientId)
                .map { it.toResponse() }
    }

    private fun requireMedicine(medicineId: Long): Medicine {
        return medicineRepository.findByIdOrNull(medicineId)
                ?: throw MedicineNotFoundException(medicineId)
    }
}