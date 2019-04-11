package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.service.NoPendingDraftException
import com.itmo.mpa.service.StatusService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(value = "/draft")
@RequestMapping("patients")
class DraftController(private val statusService: StatusService) {

    @ApiOperation("Commit draft")
    @PostMapping("{patientId}/status/draft")
    fun commitDraft(@PathVariable patientId: Long): StatusResponse {
        return statusService.commitDraft(patientId)
    }

    @ApiOperation("Create draft")
    @PutMapping("{patientId}/status/draft")
    @ResponseStatus(HttpStatus.CREATED)
    fun createDraft(
            @PathVariable patientId: Long,
            @Valid @RequestBody draftRequest: StatusRequest
    ) {
        statusService.rewriteDraft(patientId, draftRequest)
    }

    @ApiOperation("Get current draft")
    @GetMapping("{patientId}/status/draft")
    fun getDraftByPatientId(@PathVariable patientId: Long): ResponseEntity<StatusResponse> {
        val draft = statusService.findDraft(patientId)
                ?: throw NoPendingDraftException(patientId)
        return ResponseEntity.ok(draft)
    }
}