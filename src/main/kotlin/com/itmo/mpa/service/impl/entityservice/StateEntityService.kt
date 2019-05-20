package com.itmo.mpa.service.impl.entityservice

import com.itmo.mpa.entity.Disease
import com.itmo.mpa.entity.State
import com.itmo.mpa.repository.StateRepository
import com.itmo.mpa.service.exception.NoInitialStateException
import org.springframework.stereotype.Service

@Service
class StateEntityService(
        private val stateRepository: StateRepository
) {

    fun findInitialState(disease: Disease): State {
        return stateRepository.findInitialState(disease)
                ?: throw NoInitialStateException(disease.name)
    }
}
