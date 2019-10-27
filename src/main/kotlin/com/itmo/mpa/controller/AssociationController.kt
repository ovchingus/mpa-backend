package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.AssociationRequest
import com.itmo.mpa.dto.response.AssociationResponse
import com.itmo.mpa.service.AssociationService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("associations")
class AssociationController(
    private val associationService: AssociationService
) {

    @GetMapping
    @ApiOperation("Get relevant associations")
    fun getAllAssociations(
        @RequestParam patientId: Long
    ): List<AssociationResponse> = associationService.getRelevantAssociations(patientId)

    @PostMapping("{doctorId}")
    @ApiOperation("Save new association")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAssociation(
        @PathVariable doctorId: Long,
        @Valid @RequestBody request: AssociationRequest
    ): AssociationResponse = associationService.createAssociation(doctorId, request)

    @PutMapping("{associationId}")
    @ApiOperation("Replace existing association")
    fun replaceAssociation(
        @PathVariable associationId: Long,
        @Valid @RequestBody request: AssociationRequest
    ): AssociationResponse = associationService.replaceAssociation(associationId, request)

    @DeleteMapping("{associationId}")
    @ApiOperation("Delete an association")
    fun deleteAssociation(
        @PathVariable associationId: Long
    ): Unit = associationService.deleteAssociation(associationId)
}
