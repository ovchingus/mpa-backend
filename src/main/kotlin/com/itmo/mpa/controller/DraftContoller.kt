package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.DraftRequest
import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.service.StatusService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(value = "/draft")
@RequestMapping("draft")
class DraftContoller(private val statusService: StatusService) {

    @ApiOperation("Commit draft")
    @PostMapping("patients/{id}/status/draft")
    //TODO: смотрите в описание issue, этот метод делает две вещи
    fun commitDraft(@PathVariable id: Int) {
        statusService.commitDraft(id)
    }

    @ApiOperation("Create draft")
    @PutMapping("patients/{id}/status/draft")
    fun createDraft(@PathVariable id: Int, @Valid @RequestBody draftRequest: DraftRequest) {
        statusService.createDraft(id, draftRequest)
    }

    @ApiOperation("Get current draft")
    @GetMapping("patients/{id}/status/draft")
    fun getStatusDraftByPatientId(@PathVariable id: Int) : ResponseEntity<DraftResponse> {
        val draft = statusService.findStatusDraft(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(draft)
    }
}