package com.itmo.mpa.controller

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StatusResponse
import com.itmo.mpa.service.AttributeService
import com.itmo.mpa.service.StatusService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api("/patients/{patientId}")
@RequestMapping("patients/{patientId}")
class StatusController(
        private val statusService: StatusService,
        private val attributeService: AttributeService
) {

    @ApiOperation("Collect patient's history")
    @GetMapping("statuses")
    fun collectPatientStatuses(
            @PathVariable patientId: Long
    ): List<StatusResponse> = statusService.findAllForPatient(patientId)

    @ApiOperation("Get current patient's status")
    @GetMapping("status")
    fun getCurrentStatus(@PathVariable patientId: Long): StatusResponse = statusService.findCurrentStatus(patientId)

    @ApiOperation("Get patient's status by id")
    @GetMapping("status/{statusId}")
    fun getStatusById(
            @PathVariable patientId: Long,
            @PathVariable statusId: Long
    ): StatusResponse = statusService.findStatusById(patientId, statusId)

    @GetMapping("status/attributes")
    @ApiOperation("Get available disease attributes")
    fun getDiseaseAttributesByPatientId(
            @PathVariable patientId: Long
    ): List<DiseaseAttributeResponse> = attributeService.getDiseaseAttributes(patientId)
}