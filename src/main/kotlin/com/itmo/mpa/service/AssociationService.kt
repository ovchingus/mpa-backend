package com.itmo.mpa.service

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.service.exception.DoctorNotFoundException
import com.itmo.mpa.service.exception.PatientNotFoundException

interface AssociationService {

    /**
     *  Returns list of all associations by [doctorId].
     *  [AssociationResponse.isRelevant] will be set according to the
     *  value produced by predicate of the association. It wil be set to `null` in case of any error occurred.
     *  If that's the case, [AssociationResponse.errorCause] will be populated with description
     *
     *  @param doctorId doctor id
     *  @param patientId patient id to
     *  @return found associations
     *  @throws PatientNotFoundException if [patientId] is not `null` and not found
     *  @throws DoctorNotFoundException if doctor not found by [doctorId]
     */
    fun getDoctorsAssociations(doctorId: Long, patientId: Long?): List<AssociationResponse>

    /**
     *  Creates a new association for a doctor
     *
     *  @param doctorId doctor id
     *  @param request valid association data
     *  @return populated association response
     *  @throws DoctorNotFoundException if doctor not found by [doctorId]
     */
    fun createAssociation(doctorId: Long, request: AssociationRequest): AssociationResponse
}