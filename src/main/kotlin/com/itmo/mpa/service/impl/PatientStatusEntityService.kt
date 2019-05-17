package com.itmo.mpa.service.impl

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.exception.PatientNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class PatientStatusEntityService(
        private val patientRepository: PatientRepository,
        private val statusRepository: StatusRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findPatient(patientId: Long): Patient {
        logger.info("findPatient: {}", patientId)
        return patientRepository.findByIdOrNull(patientId) ?: throw PatientNotFoundException(patientId)
    }

    fun findDraftWithPatient(patientId: Long): Pair<Status?, Patient> {
        val patient = findPatient(patientId)
        return statusRepository.findStatusByPatientAndDraft(patient, draft = true) to patient
    }

    fun requireDraftWithPatient(patientId: Long?): Pair<Status?, Patient?> {
        if (patientId == null) return Pair(null, null)
        return requireDraftWithPatient(patientId)
    }

    fun requireDraftWithPatient(patientId: Long): Pair<Status, Patient> {
        val (status, patient) = findDraftWithPatient(patientId)
        if (status == null) {
            throw NoPendingDraftException(patientId)
        }
        return status to patient
    }
}