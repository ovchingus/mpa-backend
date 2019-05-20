package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.entity.Association

fun AssociationRequest.toEntity() = Association().also {
    it.text = text!!
    it.predicate = predicate!!
}

fun Association.toResponse() = AssociationResponse(id, text, createdDate)
