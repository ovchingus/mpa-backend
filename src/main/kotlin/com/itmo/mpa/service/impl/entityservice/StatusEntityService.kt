package com.itmo.mpa.service.impl.entityservice

import com.itmo.mpa.entity.Disease
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.StatusRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class StatusEntityService(
        private val stateEntityService: StateEntityService,
        private val statusRepository: StatusRepository
) {

    fun createInitialStatus(patient: Patient, disease: Disease): Status {
        val status = Status().apply {
            draft = false
            submittedOn = Instant.now()
            state = stateEntityService.findInitialState(disease)
            this.patient = patient
        }
        return statusRepository.save(status)
    }
}