package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.AvailableTransitionResponse
import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.service.StatusService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(value = "/patients/{patientId}")
@RequestMapping("patients/{patientId}")
class DraftController(private val statusService: StatusService) {

    @ApiOperation("Commit draft")
    @PostMapping("status/draft")
    fun commitDraft(@PathVariable patientId: Long): StatusResponse = statusService.commitDraft(patientId)

    @ApiOperation("Create draft")
    @PutMapping("status/draft")
    @ResponseStatus(HttpStatus.CREATED)
    fun createDraft(
            @PathVariable patientId: Long,
            @Valid @RequestBody draftRequest: StatusRequest
    ): Unit = statusService.rewriteDraft(patientId, draftRequest)

    @ApiOperation("Get current draft")
    @GetMapping("status/draft")
    fun getDraftByPatientId(@PathVariable patientId: Long): StatusResponse = statusService.findDraft(patientId)

    @ApiOperation("Get available disease attributes")
    @GetMapping("status/draft/attributes")
    fun getDiseaseAttributesByPatientId(
            @PathVariable patientId: Long
    ): List<DiseaseAttributeResponse> = statusService.getDiseaseAttributes(patientId)

    @ApiOperation("Get available transitions from current state")
    @GetMapping("status/draft/states")
    fun getAvailableTransitionsByPatientId(
            @PathVariable patientId: Long
    ): List<AvailableTransitionResponse> = statusService.getAvailableTransitions(patientId)
}