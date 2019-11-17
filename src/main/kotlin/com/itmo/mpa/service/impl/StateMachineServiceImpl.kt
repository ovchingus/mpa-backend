package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.response.StateImageResponse
import com.itmo.mpa.dto.response.StateMachineResponse
import com.itmo.mpa.entity.states.State
import com.itmo.mpa.repository.StateImageRepository
import com.itmo.mpa.repository.StateRepository
import com.itmo.mpa.repository.TransitionRepository
import com.itmo.mpa.service.StateMachineService
import com.itmo.mpa.service.impl.entityservice.DiseaseEntityService
import com.itmo.mpa.service.mapping.toResponse
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class StateMachineServiceImpl(
        private val diseaseEntityService: DiseaseEntityService,
        private val stateRepository: StateRepository,
        private val transitionRepository: TransitionRepository,
        private val stateImageRepository: StateImageRepository
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

    override fun getImageState(stateId: Long): StateImageResponse {
        val state: State = stateRepository.findById(stateId).get()
        val stateImage = stateImageRepository.findByMachineState(state.name)
        val imageFile = File(stateImage.picture)
        return stateImage.toResponse(imageFile)
    }
}
