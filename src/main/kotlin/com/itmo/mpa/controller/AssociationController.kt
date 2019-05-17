package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.service.AssociationService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api("/doctors/{doctorId}/associations")
@RequestMapping("doctors/{doctorId}/associations")
class AssociationController(
        private val associationService: AssociationService
) {

    @GetMapping
    @ApiOperation("Get all doctor's associations")
    fun getAllAssociations(
            @PathVariable doctorId: Long,
            @RequestParam(required = false) patientId: Long?
    ): List<AssociationResponse> = associationService.getDoctorsAssociations(doctorId, patientId)

    @PostMapping
    @ApiOperation("Save new association")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAssociation(
            @PathVariable doctorId: Long,
            @Valid @RequestBody request: AssociationRequest
    ): AssociationResponse = associationService.createAssociation(doctorId, request)
}
