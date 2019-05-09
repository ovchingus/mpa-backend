package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.response.AvailableTransitionResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.entity.Transition
import com.itmo.mpa.repository.TransitionRepository
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.TransitionService
import com.itmo.mpa.service.mapping.toResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransitionServiceImpl(
        private val transitionRepository: TransitionRepository,
        private val predicateService: PredicateService,
        private val patientStatusEntityService: PatientStatusEntityService
) : TransitionService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getAvailableTransitions(patientId: Long): List<AvailableTransitionResponse> {
        val (status, patient) = patientStatusEntityService.requireDraftWithPatient(patientId)
        return transitionRepository.findByStateFrom(status.state)
                .map { transition -> formAvailableTransitionResponse(patient, status, transition) }
                .also { logger.debug("getAvailableTransitions: result {}", it) }
    }

    private fun formAvailableTransitionResponse(
            patient: Patient,
            status: Status,
            transition: Transition
    ): AvailableTransitionResponse {
        val stateResponse = transition.stateTo.toResponse()
        return try {
            val testResult = predicateService.testPredicate(patient, status, transition.predicate)
            AvailableTransitionResponse(stateResponse, testResult, errorCause = null)
        } catch (e: Exception) {
            AvailableTransitionResponse(stateResponse, isRecommended = null, errorCause = e.message)
        }
    }
}