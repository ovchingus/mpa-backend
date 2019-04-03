package com.itmo.mpa.repository

import com.itmo.mpa.entity.Draft
import com.itmo.mpa.entity.Patient
import org.springframework.data.repository.CrudRepository

interface DraftRepository : CrudRepository<Draft, Long> {

    fun findDraftByPatient(patient: Patient): Draft?
}