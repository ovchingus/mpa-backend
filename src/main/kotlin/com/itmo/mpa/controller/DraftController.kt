package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.NoPendingDraftException
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
    //TODO: смотрите в описание issue, этот метод делает две вещи
    fun commitDraft(@PathVariable patientId: Long) {
        statusService.commitDraft(patientId)
    }

    @ApiOperation("Create draft")
    @PutMapping("{patientId}/status/draft")
    @ResponseStatus(HttpStatus.CREATED)
    fun createDraft(
            @PathVariable patientId: Long,
            @Valid @RequestBody draftRequest: DraftRequest
    ) {
        statusService.rewriteDraft(patientId, draftRequest)
    }

    @ApiOperation("Get current draft")
    @GetMapping("{patientId}/status/draft")
    fun getDraftByPatientId(@PathVariable patientId: Long): ResponseEntity<DraftResponse> {
        val draft = statusService.findDraft(patientId)
                ?: throw NoPendingDraftException(patientId)
        return ResponseEntity.ok(draft)
    }
}