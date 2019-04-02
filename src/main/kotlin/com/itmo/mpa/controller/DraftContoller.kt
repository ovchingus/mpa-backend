package com.itmo.mpa.controller

import com.itmo.mpa.dto.response.DraftResponse
import com.itmo.mpa.service.StatusService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Api(value = "/draft")
@RequestMapping("draft")
class DraftContoller(private val statusService: StatusService) {

    @ApiOperation("Commit draft")
    @PostMapping
    //TODO: смотрите в описание issue, этот метод делает две вещи
    fun commitDraft() {

    }

    @ApiOperation("Create draft")
    @PutMapping
    fun createDraft() {

    }

    @ApiOperation("Get current draft")
    @GetMapping("{id}/patients/status/draft")
    fun getStatusDraftByPatientId(@PathVariable id: Int) : ResponseEntity<DraftResponse> {
        val draft = statusService.findStatusDraft(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(draft)
    }
}