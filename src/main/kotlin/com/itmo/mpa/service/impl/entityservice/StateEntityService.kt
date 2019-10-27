package com.itmo.mpa.service.impl.entityservice

import com.itmo.mpa.entity.Disease
import com.itmo.mpa.entity.states.State
import com.itmo.mpa.repository.StateRepository
import com.itmo.mpa.service.exception.NoInitialStateException
import org.springframework.stereotype.Service

@Service
class StateEntityService(
    private val stateRepository: StateRepository
) {

    fun findInitialState(disease: Disease): State {
        return runCatching { stateRepository.findInitialState(disease) }.getOrNull()
                ?: throw NoInitialStateException(disease.name)
    }
}
