package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.entity.Association

fun AssociationRequest.toEntity() = Association().also {
    it.text = text!!
}
