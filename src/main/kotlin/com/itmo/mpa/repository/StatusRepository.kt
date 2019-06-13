package com.itmo.mpa.repository

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import org.springframework.data.repository.PagingAndSortingRepository

interface StatusRepository : PagingAndSortingRepository<Status, Long> {

    fun findStatusByPatientAndDraft(patient: Patient, draft: Boolean): Status?

    fun findStatusByPatientAndId(patient: Patient, id: Long): Status?

    fun findStatusesByPatientAndDraftOrderBySubmittedOnAsc(patient: Patient, draft: Boolean): List<Status>
}
