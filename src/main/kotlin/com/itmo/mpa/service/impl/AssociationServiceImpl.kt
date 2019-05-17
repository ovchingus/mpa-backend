package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.service.AssociationService
import org.springframework.stereotype.Service

@Service
class AssociationServiceImpl : AssociationService {

    override fun getDoctorsAssociations(doctorId: Long, patientId: Long?): List<AssociationResponse> {
        TODO("not implemented")
    }

    override fun createAssociation(doctorId: Long, request: AssociationRequest): AssociationResponse {
        TODO("not implemented")
    }
}