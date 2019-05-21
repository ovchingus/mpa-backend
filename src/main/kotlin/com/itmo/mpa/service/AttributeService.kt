package com.itmo.mpa.service

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.service.exception.PatientNotFoundException

interface AttributeService {

    /**
     *  Returns list of all disease attributes which could be saved within a draft
     *  and committed later on for patient by given [patientId]
     *
     *  @param patientId patient id
     *  @return found attributes
     *  @throws PatientNotFoundException if patient not found
     */
    fun getDiseaseAttributes(patientId: Long): List<DiseaseAttributeResponse>

    /**
     *  Returns list of all disease attributes for a given state
     *
     *  @param stateId patient id
     *  @return found attributes
     */
    fun getForState(stateId: Long): List<DiseaseAttributeResponse>
}