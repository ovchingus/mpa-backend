package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.service.AssociationService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("doctors/{doctorId}/associations")
class AssociationController(
        private val associationService: AssociationService
) {

    @GetMapping
    @ApiOperation("Get all doctor's associations")
    fun getAllAssociations(
            @PathVariable doctorId: Long
    ): List<AssociationResponse> = associationService.getDoctorsAssociations(doctorId, patientId = null)

    @PostMapping
    @ApiOperation("Save new association")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAssociation(
            @PathVariable doctorId: Long,
            @Valid @RequestBody request: AssociationRequest
    ): AssociationResponse = associationService.createAssociation(doctorId, request)

    @PutMapping("{associationId}")
    @ApiOperation("Replace existing association")
    fun replaceAssociation(
            @PathVariable doctorId: Long,
            @PathVariable associationId: Long,
            @Valid @RequestBody request: AssociationRequest
    ): AssociationResponse = associationService.replaceAssociation(doctorId, associationId, request)

    @DeleteMapping("{associationId}")
    @ApiOperation("Delete an association")
    fun deleteAssociation(
            @PathVariable doctorId: Long,
            @PathVariable associationId: Long
    ): Unit = associationService.deleteAssociation(doctorId, associationId)
}
