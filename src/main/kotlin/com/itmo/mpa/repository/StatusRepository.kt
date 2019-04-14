package com.itmo.mpa.repository

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import org.springframework.data.repository.CrudRepository

interface StatusRepository : CrudRepository<Status, Long> {

    fun findStatusByPatientAndDraft(patient: Patient, draft: Boolean): Status?
}