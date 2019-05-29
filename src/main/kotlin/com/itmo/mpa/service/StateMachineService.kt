package com.itmo.mpa.service

import com.itmo.mpa.dto.response.StateMachineResponse
import com.itmo.mpa.service.exception.DiseaseNotFoundException

interface StateMachineService {

    /**
     *  Returns a graph for the disease by given [diseaseId]
     *
     *  @return a state machine for the disease
     *  @throws DiseaseNotFoundException if disease not found by [diseaseId]
     */
    fun getStateMachineForDisease(diseaseId: Long): StateMachineResponse
}