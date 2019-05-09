package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.response.AppropriateMedicineResponse
import com.itmo.mpa.entity.Contraindications
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.ContraindicationsRepository
import com.itmo.mpa.service.MedicineService
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MedicineServiceImpl(
        private val patientStatusEntityService: PatientStatusEntityService,
        private val contraindicationsRepository: ContraindicationsRepository,
        private val predicateService: PredicateService
) : MedicineService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getAppropriateMedicine(patientId: Long): List<AppropriateMedicineResponse> {
        val (status, patient) = patientStatusEntityService.requireDraftWithPatient(patientId)
        return contraindicationsRepository.findAll()
                .map { contraindication -> formAppropriateMedicineResponse(patient, status, contraindication) }
                .also { logger.debug("getAvailableTransitions: result {}", it) }
    }

    private fun formAppropriateMedicineResponse(
            patient: Patient,
            status: Status,
            contraindication: Contraindications
    ): AppropriateMedicineResponse {
        val medicineResponse = contraindication.medicine.toResponse()
        return try {
            val isContraindicated = predicateService.testPredicate(
                    patient,
                    status,
                    contraindication.predicate
            )
            AppropriateMedicineResponse(medicineResponse, isNotRecommended = isContraindicated, errorCause = null)
        } catch (e: Exception) {
            AppropriateMedicineResponse(medicineResponse, isNotRecommended = null, errorCause = e.message)
        }
    }
}