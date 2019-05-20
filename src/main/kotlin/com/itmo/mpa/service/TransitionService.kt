package com.itmo.mpa.service

import com.itmo.mpa.dto.response.AvailableTransitionResponse
import com.itmo.mpa.service.exception.PatientNotFoundException

interface TransitionService {

    /**
     *  Returns list of all available transitions that are directly descendant from
     *  current state for patient by [patientId].
     *  [AvailableTransitionResponse.isRecommended] will be set according to the
     *  value produced by predicate of state's transition. It wil be set to `null` in case of any error occurred.
     *  If that's the case, [AvailableTransitionResponse.errorCause] will be populated with description
     *
     *  @param patientId patient id
     *  @return found available transitions
     *  @throws PatientNotFoundException if patient not found
     */
    fun getAvailableTransitions(patientId: Long): List<AvailableTransitionResponse>
}
