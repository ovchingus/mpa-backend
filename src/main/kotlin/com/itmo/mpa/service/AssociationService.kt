package com.itmo.mpa.service

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.service.exception.AssociationNotFoundException
import com.itmo.mpa.service.exception.AssociationTypeNotFoundException
import com.itmo.mpa.service.exception.DoctorNotFoundException
import com.itmo.mpa.service.exception.PatientNotFoundException
import com.itmo.mpa.service.impl.predicate.parser.exception.UnexpectedTokenException

interface AssociationService {

    /**
     *  Returns list of all associations by [doctorId].
     *
     *  @param doctorId doctor id
     *  @return found associations
     *  @throws DoctorNotFoundException if doctor not found by [doctorId]
     */
    fun getDoctorsAssociations(doctorId: Long): List<AssociationResponse>

    /**
     *  Returns list of all the relevant associations by [patientId].
     *
     *  @param patientId patient id as a predicate context
     *  @return found associations
     *  @throws PatientNotFoundException if not found by [patientId]
     */
    fun getRelevantAssociations(patientId: Long): List<AssociationResponse>

    /**
     *  Creates a new association for a doctor
     *
     *  @param doctorId doctor id
     *  @param request valid association data
     *  @return populated association response
     *  @throws DoctorNotFoundException if doctor not found by [doctorId]
     *  @throws AssociationTypeNotFoundException if association type not found
     *  @throws UnexpectedTokenException if predicate is malformed
     */
    fun createAssociation(doctorId: Long, request: AssociationRequest): AssociationResponse

    /**
     *  Replaces an association with a new one
     *
     *  @param associationId association id to replace
     *  @param request valid association data
     *  @return populated association response
     *  @throws AssociationNotFoundException if association not found by [associationId]
     *  @throws AssociationTypeNotFoundException if association type not found
     *  @throws UnexpectedTokenException if predicate is malformed
     */
    fun replaceAssociation(associationId: Long, request: AssociationRequest): AssociationResponse

    /**
     *  Deletes an association
     *
     *  @param associationId association id to delete
     *  @throws AssociationNotFoundException if association not found by [associationId]
     */
    fun deleteAssociation(associationId: Long)
}