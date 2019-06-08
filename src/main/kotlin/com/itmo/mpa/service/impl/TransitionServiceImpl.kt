package com.itmo.mpa.service.impl

import arrow.core.getOrHandle
import com.itmo.mpa.dto.response.AvailableTransitionResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.entity.Transition
import com.itmo.mpa.repository.TransitionRepository
import com.itmo.mpa.service.PredicateService
import com.itmo.mpa.service.TransitionService
import com.itmo.mpa.service.impl.entityservice.PatientStatusEntityService
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
        val (draft, patient) = patientStatusEntityService.findDraftWithPatient(patientId)
        return transitionRepository.findByStateFrom(patient.currentStatus.state)
                .map { it.formResponse(patient, draft) }
                .also { logger.debug("getAvailableTransitions: result {}", it) }
    }

    private fun Transition.formResponse(
            patient: Patient,
            draft: Status?
    ): AvailableTransitionResponse {
        val stateResponse = stateTo.toResponse()
        return predicateService.testPredicate(patient, draft, predicate)
                .map { testResult -> AvailableTransitionResponse(stateResponse, testResult, errors = null) }
                .getOrHandle { e ->
                    AvailableTransitionResponse(
                            stateResponse,
                            isRecommended = null,
                            errors = e.errors.map { it.toResponse() }
                    )
                }
    }
}
