package com.itmo.mpa.service

import com.itmo.mpa.dto.response.StateMachineResponse
import com.itmo.mpa.service.exception.DiseaseNotFoundException
import java.io.File

interface StateMachineService {

    /**
     *  Returns a graph for the disease by given [diseaseId]
     *
     *  @return a state machine for the disease
     *  @throws [DiseaseNotFoundException] if disease not found by [diseaseId]
     */
    fun getStateMachineForDisease(diseaseId: Long): StateMachineResponse

    /**
     *  Returns image for a given state
     *
     *  @param stateId patient id
     *  @return found image
     */
    fun getImageState(stateId: Long): File
}
