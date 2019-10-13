package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.entity.associations.Association
import com.itmo.mpa.entity.Doctor

fun AssociationRequest.toEntity(doctor: Doctor) = Association().also {
    it.text = this.text!!
    it.predicate = this.predicate!!
    it.doctor = doctor
}

fun Association.toResponse() = AssociationResponse(
        id = this.id,
        text = this.text,
        type = this.associationType.name.toLowerCase(),
        createdOn = this.createdDate
)
