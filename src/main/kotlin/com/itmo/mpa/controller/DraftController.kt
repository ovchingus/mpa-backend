package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.*
import com.itmo.mpa.service.*
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(value = "/patients/{patientId}/status/draft")
@RequestMapping("patients/{patientId}/status/draft")
class DraftController(
        private val statusService: StatusService,
        private val draftService: DraftService,
        private val transitionService: TransitionService,
        private val medicineService: MedicineService,
        private val associationService: AssociationService
) {

    @PostMapping
    @ApiOperation("Commit draft")
    fun commitDraft(@PathVariable patientId: Long): StatusResponse = statusService.commitDraft(patientId)

    @PutMapping
    @ApiOperation("Create draft")
    @ResponseStatus(HttpStatus.CREATED)
    fun createDraft(
            @PathVariable patientId: Long,
            @Valid @RequestBody draftRequest: StatusRequest
    ): Unit = draftService.rewriteDraft(patientId, draftRequest)

    @GetMapping
    @ApiOperation("Get current draft")
    fun getDraftByPatientId(@PathVariable patientId: Long): StatusResponse = draftService.findDraft(patientId)

    @GetMapping("attributes")
    @ApiOperation("Get available disease attributes")
    fun getDiseaseAttributesByPatientId(
            @PathVariable patientId: Long
    ): List<DiseaseAttributeResponse> = draftService.getDiseaseAttributes(patientId)

    @GetMapping("states")
    @ApiOperation("Get available transitions from current state")
    fun getAvailableTransitionsByPatientId(
            @PathVariable patientId: Long
    ): List<AvailableTransitionResponse> = transitionService.getAvailableTransitions(patientId)

    @GetMapping("medicine")
    @ApiOperation("Get medicine compatibility info from current state")
    fun getAppropriateMedicinesByPatientId(
            @PathVariable patientId: Long
    ): List<AppropriateMedicineResponse> = medicineService.getAppropriateMedicine(patientId)

    @GetMapping("associations")
    @ApiOperation("Get relevant associations for a patient")
    fun getRelevantAssociationsByPatientId(
            @PathVariable patientId: Long
    ): List<AssociationResponse> = associationService.getRelevantAssociations(patientId)
}
