package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.response.StateMachineResponse
import com.itmo.mpa.repository.StateRepository
import com.itmo.mpa.repository.TransitionRepository
import com.itmo.mpa.service.StateMachineService
import com.itmo.mpa.service.impl.entityservice.DiseaseEntityService
import com.itmo.mpa.service.mapping.toResponse
import org.springframework.stereotype.Service

@Service
class StateMachineServiceImpl(
        private val diseaseEntityService: DiseaseEntityService,
        private val stateRepository: StateRepository,
        private val transitionRepository: TransitionRepository
) : StateMachineService {

    override fun getStateMachineForDisease(diseaseId: Long): StateMachineResponse {
        val disease = diseaseEntityService.findDisease(diseaseId)
        val diseaseStates = stateRepository.findByDisease(disease)
        val stateTransitions = transitionRepository.findByStateFromIn(diseaseStates)
        return StateMachineResponse(
                states = diseaseStates.map { it.toResponse() },
                transitions = stateTransitions.map { it.toResponse() }
        )
    }
}
